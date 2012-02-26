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
package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class ParsedOptionSpecificationBuilder extends AbstractOptionSpecificationBuilder implements OptionSpecificationBuilder {
    private final List<String> m_shortNames = new ArrayList<String>();
    private List<String> m_longName;
    private boolean m_helpRequest;

    public ParsedOptionSpecificationBuilder(final ReflectedMethod method) {
        super(method);
    }

    public void setShortNames(final List<String> shortNames) {
        m_shortNames.addAll(shortNames);
    }

    public void setLongName(final List<String> longName) {
        m_longName = longName;
    }

    public void setHelpRequest(final boolean helpRequest) {
        m_helpRequest = helpRequest;
    }

    public ParsedOptionSpecification createOptionSpecification() {
        final OptionName optionName =
                new OptionName(method, method.propertyName(), m_longName, m_shortNames, description);
        final OptionType optionType = new OptionType(type.classUnderReflection(), pattern, multiValued);
        final OptionContext optionContext = new OptionContext(defaultValue, hidden, m_helpRequest, defaultToNull);

        return new ParsedOptionSpecificationImpl(optionName,
                optionType,
                optionContext,
                method,
                optionalityMethod);
    }
}
