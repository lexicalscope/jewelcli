package com.lexicalscope.jewel.cli;

import static java.util.Arrays.asList;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class AbstractConvertMethodToOptionSpecification {
    protected final ReflectedClass<?> klass;

    public AbstractConvertMethodToOptionSpecification(final ReflectedClass<?> klass) {
        this.klass = klass;
    }

    protected UnparsedOptionSpecification createUnparsedOptionSpecificationFrom(
            final ReflectedMethod method) {
        final UnparsedOptionSpecificationBuilder optionSpecificationBuilder =
                new UnparsedOptionSpecificationBuilder(method);

        final UnparsedAnnotationAdapter annotation =
                new UnparsedAnnotationAdapter(klass, method, method.annotation(Unparsed.class));

        configureSpecificationFromAnnotation(optionSpecificationBuilder, annotation);

        optionSpecificationBuilder.setValueName(annotation.name());

        return optionSpecificationBuilder.createOptionSpecification();
    }

    protected ParsedOptionSpecification createParsedOptionSpecificationFrom(
            final ReflectedMethod method) {
        final ParsedOptionSpecificationBuilder optionSpecificationBuilder =
                new ParsedOptionSpecificationBuilder(method);

        final OptionAnnotationAdapter annotation = new OptionAnnotationAdapter(klass, method);

        optionSpecificationBuilder.setShortNames(annotation.shortName());
        optionSpecificationBuilder.setLongName(annotation.longName());
        optionSpecificationBuilder.setHelpRequest(annotation.helpRequest());

        configureSpecificationFromAnnotation(
                optionSpecificationBuilder,
                annotation);

        return optionSpecificationBuilder.createOptionSpecification();
    }

    private void configureSpecificationFromAnnotation(
            final OptionSpecificationBuilder optionSpecificationBuilder,
            final OptionAdapter annotation) {

        optionSpecificationBuilder.setDescription(annotation.description().trim());
        optionSpecificationBuilder.setPattern(annotation.pattern());
        optionSpecificationBuilder.setOptionalityMethod(annotation.correspondingOptionalityMethod());
        optionSpecificationBuilder.setType(annotation.getValueType());
        optionSpecificationBuilder.setMultiValued(annotation.isMultiValued());
        optionSpecificationBuilder.setHidden(annotation.isHidden());

        if (annotation.defaultToNull() && annotation.hasDefaultValue())
        {
            throw new InvalidOptionSpecificationException("option cannot have null default and non-null default value: "
                    + annotation.method());
        }
        else if (annotation.defaultToNull())
        {
            optionSpecificationBuilder.setDefaultToNull(true);
            if (annotation.isMultiValued())
            {
                optionSpecificationBuilder.setDefaultValue(null);
            }
            else
            {
                optionSpecificationBuilder.setDefaultValue(asList((String) null));
            }
        }
        else if (annotation.hasDefaultValue())
        {
            optionSpecificationBuilder.setDefaultValue(asList(annotation.defaultValue()));
        }
    }
}
