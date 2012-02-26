/*
 * Copyright 2009 Tim Wood
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
package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class ParsedOptionSpecificationImpl implements Comparable<OptionSpecification>, ParsedOptionSpecification {
    private final OptionName optionName;
    private final OptionType optionType;
    private final OptionContext optionContext;
    private final ReflectedMethod method;
    private final ReflectedMethod optionalityMethod;

    ParsedOptionSpecificationImpl(
            final OptionName optionName,
            final OptionType optionType,
            final OptionContext optionContext,
            final ReflectedMethod method,
            final ReflectedMethod optionalityMethod) {
        this.optionName = optionName;
        this.optionType = optionType;
        this.optionContext = optionContext;
        this.method = method;
        this.optionalityMethod = optionalityMethod;
    }

    @Override public List<String> getDefaultValue() {
        return optionContext.getDefaultValue();
    }

    @Override public String getDescription() {
        return optionName.getDescription();
    }

    @Override public List<String> getLongName() {
        return optionName.getLongNames();
    }

    @Override public List<String> getShortNames() {
        return optionName.getShortNames();
    }

    @Override public List<String> getNames() {
        final List<String> result = new ArrayList<String>(getShortNames());
        result.addAll(getLongName());
        return result;
    }

    @Override public boolean hasDefaultValue() {
        return getDefaultValue() != null || optionContext.isDefaultToNull();
    }

    @Override public boolean hasShortName() {
        return !getShortNames().isEmpty();
    }

    @Override public boolean isHelpOption() {
        return optionContext.isHelpRequest();
    }

    @Override public String getPattern() {
        return optionType.getPattern();
    }

    @Override public Class<?> getType() {
        return optionType.getType();
    }

    @Override public boolean hasValue() {
        return !isBoolean();
    }

    @Override public boolean isMultiValued() {
        return optionType.isMultiValued();
    }

    @Override public boolean isOptional() {
        return optionalityMethod != null || isBoolean() || hasDefaultValue();
    }

    @Override public final boolean isBoolean() {
        return getType().isAssignableFrom(Boolean.class) || getType().isAssignableFrom(boolean.class);
    }

    @Override public String getCanonicalIdentifier() {
        return method.propertyName();
    }

    @Override public ReflectedMethod getMethod() {
        return method;
    }

    @Override public ReflectedMethod getOptionalityMethod() {
        return optionalityMethod;
    }

    @Override public int compareTo(final OptionSpecification other) {
        return getCanonicalIdentifier().compareTo(other.getCanonicalIdentifier());
    }

    @Override public String toString() {
        return new ParsedOptionSummary(this).toString();
    }
}
