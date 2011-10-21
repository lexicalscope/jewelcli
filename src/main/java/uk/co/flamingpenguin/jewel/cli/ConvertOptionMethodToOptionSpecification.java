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
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ConvertOptionMethodToOptionSpecification implements Converter<ReflectedMethod, OptionSpecification> {
    private final ReflectedClass<?> klass;

    public ConvertOptionMethodToOptionSpecification(final ReflectedClass<?> klass) {
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

        final String longNameSpecification = optionAnnotation.longName().trim();
        final String longName = nullOrBlank(longNameSpecification) ? baseName : longNameSpecification;
        optionSpecificationBuilder.setLongName(longName);

        final String description = optionAnnotation.description().trim();
        optionSpecificationBuilder.setDescription(description);

        final String pattern = optionAnnotation.pattern();
        optionSpecificationBuilder.setPattern(pattern);

        if (optionAnnotation.defaultToNull() && optionAnnotation.defaultValue().length != 0)
        {
            throw new OptionSpecificationException("option cannot have null default and non-null default value: "
                    + method);
        }
        else if (optionAnnotation.defaultToNull())
        {
            optionSpecificationBuilder.setDefaultValue(asList((String) null));
        }
        else
        {
            optionSpecificationBuilder.setDefaultValue(asList(optionAnnotation.defaultValue()));
        }

        final boolean helpRequest = optionAnnotation.helpRequest();
        optionSpecificationBuilder.setHelpRequest(helpRequest);

        return optionSpecificationBuilder.createOptionSpecification();
    }
    private boolean nullOrBlank(final String string) {
        return string == null || string.equals("");
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
