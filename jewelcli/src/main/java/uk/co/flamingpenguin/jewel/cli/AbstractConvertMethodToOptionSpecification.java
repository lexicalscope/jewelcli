package uk.co.flamingpenguin.jewel.cli;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

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

public class AbstractConvertMethodToOptionSpecification {
    protected final ReflectedClass<?> klass;

    public AbstractConvertMethodToOptionSpecification(final ReflectedClass<?> klass) {
        this.klass = klass;
    }

    protected void configureSpecificationFromAnnotation(
            final ReflectedMethod method,
            final ParsedOptionSpecificationBuilder optionSpecificationBuilder) {
        final Option optionAnnotation = method.annotation(Option.class);

        final String[] shortNameSpecification = optionAnnotation.shortName();
        final List<String> shortNames = new ArrayList<String>();
        for (final String element : shortNameSpecification) {
            final String shortName = element.trim();
            if (shortName.length() > 0) {
                shortNames.add(element.substring(0, 1));
            }
        }
        optionSpecificationBuilder.setShortNames(shortNames);

        optionSpecificationBuilder.setLongName(optionAnnotation.longName().length == 0
                ? asList(method.propertyName())
                : asList(optionAnnotation.longName()));

        optionSpecificationBuilder.setHelpRequest(optionAnnotation.helpRequest());

        configureSpecificationFromAnnotation(
                method,
                optionSpecificationBuilder,
                new OptionAnnotationAdapter(
                        klass,
                        method,
                        optionAnnotation));
    }

    protected void configureSpecificationFromAnnotation(
            final ReflectedMethod method,
            final OptionSpecificationBuilder optionSpecificationBuilder,
            final OptionAdapter annotation) {

        optionSpecificationBuilder.setDescription(annotation.description().trim());
        optionSpecificationBuilder.setPattern(annotation.pattern());
        optionSpecificationBuilder.setOptionalityMethod(annotation.correspondingOptionalityMethod());

        if (annotation.defaultToNull() && hasDefaultValue(annotation))
        {
            throw new OptionSpecificationException("option cannot have null default and non-null default value: "
                    + method);
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
        else if (hasDefaultValue(annotation))
        {
            optionSpecificationBuilder.setDefaultValue(asList(annotation.defaultValue()));
        }
    }

    private boolean hasDefaultValue(final OptionAdapter optionAnnotation) {
        return !(optionAnnotation.defaultValue().length == 1
        && optionAnnotation.defaultValue()[0].equals(Option.stringToMarkNoDefault));
    }
}
