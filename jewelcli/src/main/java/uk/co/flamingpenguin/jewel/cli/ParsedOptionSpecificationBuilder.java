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

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ParsedOptionSpecificationBuilder implements OptionSpecificationBuilder {
    private final ReflectedMethod m_method;
    private ReflectedClass<?> m_type;
    private boolean m_multiValued;
    private ReflectedMethod optionalityMethod;
    private final List<String> m_shortNames = new ArrayList<String>();
    private List<String> m_longName;
    private String m_description;
    private String m_pattern;
    private List<String> m_defaultValue;
    private boolean m_helpRequest;
    private boolean defaultToNull;

    public ParsedOptionSpecificationBuilder(final ReflectedMethod method) {
        m_method = method;
    }

    public void setType(final ReflectedClass<?> type) {
        m_type = type;
    }

    public void setMultiValued(final boolean multiValued) {
        m_multiValued = multiValued;
    }

    @Override public void setOptionalityMethod(final ReflectedMethod optionalityMethod) {
        this.optionalityMethod = optionalityMethod;
    }

    public void setShortNames(final List<String> shortNames) {
        m_shortNames.addAll(shortNames);
    }

    public void setLongName(final List<String> longName) {
        m_longName = longName;
    }

    public void setDescription(final String description) {
        m_description = description;
    }

    public void setPattern(final String pattern) {
        m_pattern = pattern;
    }

    public void setDefaultValue(final List<String> defaultValue) {
        m_defaultValue = defaultValue;
    }

    public void setDefaultToNull(final boolean defaultToNull) {
        this.defaultToNull = defaultToNull;
    }

    public void setHelpRequest(final boolean helpRequest) {
        m_helpRequest = helpRequest;
    }

    public ParsedOptionSpecification createOptionSpecification() {
        final OptionName optionName =
                new OptionName(m_method, m_method.propertyName(), m_longName, m_shortNames, m_description);
        final OptionType optionType = new OptionType(m_type.classUnderReflection(), m_pattern, m_multiValued);
        final OptionContext optionContext = new OptionContext(m_defaultValue, m_helpRequest, defaultToNull);

        return new ParsedOptionSpecificationImpl(optionName,
                optionType,
                optionContext,
                m_method,
                optionalityMethod);
    }
}
