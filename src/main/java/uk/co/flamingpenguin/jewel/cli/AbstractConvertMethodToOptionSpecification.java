package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
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
    private final ReflectedClass<?> klass;

    public AbstractConvertMethodToOptionSpecification(final ReflectedClass<?> klass) {
        this.klass = klass;
    }

    protected final ReflectedMethod findCorrespondingOptionalityMethod(final String name, final ReflectedClass<?> klass) {
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

    static final boolean isMultiValued(final ReflectedClass<?> methodType) {
        return methodType.isType(reflectedTypeReflectingOn(Collection.class));
    }

    static final ReflectedClass<? extends Object> getValueTypeFromMethodType(
            final ReflectedClass<?> methodType) {
        final boolean multiValued = isMultiValued(methodType);

        final ReflectedClass<? extends Object> valueType =
                multiValued
                        ? methodType.asType(reflectedTypeReflectingOn(Collection.class)).typeArgument(0)
                        : methodType;

        return reflectedTypeReflectingOn(Object.class).matches(valueType)
                ? type(String.class)
                : valueType;
    }
    protected final void configureOptionalityMethod(
            final ReflectedMethod method,
            final OptionSpecificationBuilder optionSpecificationBuilder) {
        final ReflectedMethod optionalityMethod = findCorrespondingOptionalityMethod(method.propertyName(), klass);
        if (optionalityMethod != null) {
            optionSpecificationBuilder.setOptionalityMethod(optionalityMethod);
        }
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

        optionSpecificationBuilder.setHelpRequest(optionAnnotation.helpRequest());
    }
}
