/*
 * Copyright 2007 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.flamingpenguin.jewel.cli;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ConvertGetterMethodToParsedOptionSpecification extends AbstractConvertMethodToOptionSpecification
        implements
        Converter<ReflectedMethod, ParsedOptionSpecification> {

    public ConvertGetterMethodToParsedOptionSpecification(final ReflectedClass<?> klass) {
        super(klass);
    }

    @Override public ParsedOptionSpecification convert(final ReflectedMethod method) {
        final ParsedOptionSpecificationBuilder optionSpecificationBuilder =
                new ParsedOptionSpecificationBuilder(method);

        final ReflectedClass<?> methodType = method.returnType();
        final OptionAnnotationAdapter annotation =
                new OptionAnnotationAdapter(klass, method, method.annotation(Option.class));

        optionSpecificationBuilder.setType(getValueTypeFromMethodType(methodType));
        optionSpecificationBuilder.setMultiValued(isMultiValued(methodType));

        configureOptionalityMethod(annotation, optionSpecificationBuilder);

        configureSpecificationFromAnnotation(method, optionSpecificationBuilder);

        return optionSpecificationBuilder.createOptionSpecification();
    }
}
