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

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class OptionSpecificationImpl implements Comparable<OptionSpecification>, OptionSpecification {
    private final OptionName optionName;
    private final OptionType m_optionType;
    private final OptionContext m_optionContext;
    private final ReflectedMethod method;
    private final ReflectedMethod optionalityMethod;

    OptionSpecificationImpl(
            final OptionName optionName,
            final OptionType optionType,
            final OptionContext optionContext,
            final ReflectedMethod method,
            final ReflectedMethod optionalityMethod) {
        this.optionName = optionName;
        m_optionType = optionType;
        m_optionContext = optionContext;
        this.method = method;
        this.optionalityMethod = optionalityMethod;
    }

    public List<String> getDefaultValue() {
        return m_optionContext.getDefaultValue();
    }

    public String getDescription() {
        return optionName.getDescription();
    }

    public List<String> getLongName() {
        return optionName.getLongNames();
    }

    public List<String> getShortNames() {
        return optionName.getShortNames();
    }

    @Override public List<String> getNames() {
        final List<String> result = new ArrayList<String>(getShortNames());
        result.addAll(getLongName());
        return result;
    }

    public boolean hasDefaultValue() {
        return !getDefaultValue().isEmpty();
    }

    public boolean hasShortName() {
        return !getShortNames().isEmpty();
    }

    public boolean isHelpOption() {
        return m_optionContext.isHelpRequest();
    }

    public String getPattern() {
        return m_optionType.getPattern();
    }

    public Class<?> getType() {
        return m_optionType.getType();
    }

    public boolean hasValue() {
        return !isBoolean();
    }

    public boolean isMultiValued() {
        return m_optionType.isMultiValued();
    }

    public boolean isOptional() {
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

    @Override public int compareTo(final OptionSpecification other) {
        return getCanonicalIdentifier().compareTo(other.getCanonicalIdentifier());
    }

    @Override public String toString() {
        return new OptionSummary(this).toString();
    }
}
