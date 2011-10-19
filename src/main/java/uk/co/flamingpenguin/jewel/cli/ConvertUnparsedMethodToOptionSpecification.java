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

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ConvertUnparsedMethodToOptionSpecification implements Converter<ReflectedMethod, OptionSpecification> {
    private static final Logger logger = Logger.getLogger(ConvertUnparsedMethodToOptionSpecification.class.getName());

    private final ReflectedClass<?> klass;

    public ConvertUnparsedMethodToOptionSpecification(final ReflectedClass<?> klass) {
        this.klass = klass;
    }

    @Override public OptionSpecification convert(final ReflectedMethod method) {
        final OptionSpecificationBuilder optionSpecificationBuilder =
                new OptionSpecificationBuilder(method.methodUnderReflection());

        final ReflectedClass<?> returnType = method.returnType();
        final boolean multiValued = returnType.isType(reflectedTypeReflectingOn(Collection.class));

        final ReflectedClass<? extends Object> type =
                multiValued
                        ? returnType.asType(reflectedTypeReflectingOn(Collection.class)).typeArgument(0)
                        : returnType;

        optionSpecificationBuilder.setType(type.classUnderReflection().equals(Object.class) ? String.class : type
                .classUnderReflection());

        optionSpecificationBuilder.setMultiValued(multiValued);

        final String baseName = method.propertyName();
        final ReflectedMethod optionalityMethod = findCorrespondingOptionalityMethod(baseName, klass);
        if (optionalityMethod != null) {
            optionSpecificationBuilder.setOptionalityMethod(optionalityMethod);
        }

        final Unparsed annotation = method.annotation(Unparsed.class);

        optionSpecificationBuilder.setLongName(annotation.name());
        optionSpecificationBuilder.setDescription("");
        optionSpecificationBuilder.setPattern(".*");
        optionSpecificationBuilder.setDefaultValue(Collections.<String>emptyList());
        optionSpecificationBuilder.setHelpRequest(false);

        return optionSpecificationBuilder.createOptionSpecification();
    }

    private final ReflectedMethod findCorrespondingOptionalityMethod(final String name, final ReflectedClass<?> klass) {
        final List<ReflectedMethod> methods =
                klass.methods(
                        callableHasName(addPrefix("is", name)).and(isExistence()));
        if (!methods.isEmpty()) {
            return methods.get(0);
        }
        return null;
    }

    private String addPrefix(final String prefix, final String name) {
        return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
