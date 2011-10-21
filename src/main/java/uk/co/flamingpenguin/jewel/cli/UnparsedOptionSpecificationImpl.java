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
package uk.co.flamingpenguin.jewel.cli;

import static uk.co.flamingpenguin.jewel.cli.OptionsSpecificationImpl.nullOrBlank;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class UnparsedOptionSpecificationImpl implements Comparable<OptionSpecification>, UnparsedOptionSpecification {
    private final OptionType m_optionType;
    private final OptionContext m_optionContext;
    private final ReflectedMethod method;
    private final ReflectedMethod optionalityMethod;
    private final String valueName;
    private final String description;

    UnparsedOptionSpecificationImpl(
            final String valueName,
            final String description,
            final OptionType optionType,
            final OptionContext optionContext,
            final ReflectedMethod method,
            final ReflectedMethod optionalityMethod) {
        this.valueName = valueName;
        this.description = description;
        m_optionType = optionType;
        m_optionContext = optionContext;
        this.method = method;
        this.optionalityMethod = optionalityMethod;
    }

    @Override public List<String> getDefaultValue() {
        return m_optionContext.getDefaultValue();
    }

    @Override public String getDescription() {
        return description;
    }

    @Override public boolean hasDefaultValue() {
        return !getDefaultValue().isEmpty();
    }

    @Override public boolean isHelpOption() {
        return m_optionContext.isHelpRequest();
    }

    @Override public Class<?> getType() {
        return m_optionType.getType();
    }

    @Override public boolean hasValue() {
        return !isBoolean();
    }

    @Override public boolean isMultiValued() {
        return m_optionType.isMultiValued();
    }

    @Override public boolean isOptional() {
        return optionalityMethod != null || isBoolean();
    }

    private final boolean isBoolean() {
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

    @Override public String getValueName() {
        return nullOrBlank(valueName) ? "ARGUMENTS" : valueName;
    }

    @Override public int compareTo(final OptionSpecification other) {
        return getCanonicalIdentifier().compareTo(other.getCanonicalIdentifier());
    }

    @Override public String toString() {
        return new UnparsedOptionSummary(this).toString();
    }
}
