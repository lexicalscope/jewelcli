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

class ParsedOptionSummary
{
    private final ParsedOptionSpecification m_option;

    public ParsedOptionSummary(final ParsedOptionSpecification option)
    {
        m_option = option;
    }

    private boolean hasCustomPattern()
    {
        return !m_option.getPattern().equals(".*");
    }

    private boolean nullOrBlank(final String description)
    {
        return description == null || description.trim().equals("");
    }

    public void describeOptionTo(final OptionHelpMessage helpMessage) {
        if (m_option.isOptional())
        {
            helpMessage.startOptionalOption();
        }
        else
        {
            helpMessage.startMandatoryOption();
        }

        helpMessage.longName(m_option.getLongName());
        helpMessage.shortName(m_option.getShortNames());

        if (m_option.hasValue())
        {
            if (m_option.isMultiValued()) {
                if (hasCustomPattern())
                {
                    helpMessage.multiValuedWithCustomPattern(m_option.getPattern());
                }
                else
                {
                    helpMessage.multiValuedWithCustomPattern();
                }
            }
            else if (hasCustomPattern())
            {
                helpMessage.singleValuedWithCustomPattern(m_option.getPattern());
            }
            else
            {
                helpMessage.singleValued();
            }
        }
        else
        {
            helpMessage.noValued();
        }

        if (m_option.isOptional())
        {
            if (hasDescription())
            {
                helpMessage.endOptionalOption(m_option.getDescription());
            } else {
                helpMessage.endOptionalOption();
            }
        }
        else
        {
            if (hasDescription())
            {
                helpMessage.endMandatoryOption(m_option.getDescription());
            } else {
                helpMessage.endMandatoryOption();
            }
        }
    }

    private boolean hasDescription() {
        return !nullOrBlank(m_option.getDescription());
    }

    @Override public String toString() {
        final OptionHelpMessage helpMessageOptionSummaryBuilderImpl =
                new HelpMessageOptionSummaryBuilderImpl();
        this.describeOptionTo(helpMessageOptionSummaryBuilderImpl);
        return helpMessageOptionSummaryBuilderImpl.toString();
    }
}
