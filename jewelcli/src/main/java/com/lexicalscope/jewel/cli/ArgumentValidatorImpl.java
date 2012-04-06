/*
 * Copyright 2006 Tim Wood
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.jewel.cli.arguments.ArgumentProcessor;
import com.lexicalscope.jewel.cli.specification.OptionSpecification;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;


class ArgumentValidatorImpl<O> implements ArgumentValidator<O>
{
    private final ValidationErrorBuilder m_validationErrorBuilder;
    private final List<String> m_validatedUnparsedArguments;
    private final Map<String, List<String>> m_validatedArguments;
    private final OptionsSpecification<O> m_specification;

    public ArgumentValidatorImpl(final OptionsSpecification<O> specification)
    {
        m_specification = specification;

        m_validatedArguments = new LinkedHashMap<String, List<String>>();
        m_validatedUnparsedArguments = new ArrayList<String>();

        m_validationErrorBuilder = new ValidationErrorBuilderImpl();
    }

    /**
     * {@inheritDoc}
     */
    public ArgumentCollection validateArguments(final ArgumentCollection arguments) throws ArgumentValidationException
    {
        arguments.forEach(new ArgumentProcessor() {
            @Override public void option(final String optionName, final List<String> allValues) {
                if (!m_specification.isSpecified(optionName))
                {
                    m_validationErrorBuilder.unexpectedOption(optionName);
                    return;
                }

                final ParsedOptionSpecification optionSpecification =
                        m_specification.getSpecification(optionName);
                if (optionSpecification.isHelpOption())
                {
                    throw new HelpRequestedException(m_specification);
                }

                if(!optionSpecification.allowedThisManyValues(allValues.size()))
                {
                    m_validationErrorBuilder.wrongNumberOfValues(optionSpecification, allValues);
                }
                else
                {
                    checkAndAddValues(
                            optionSpecification,
                            optionName,
                            new ArrayList<String>(allValues));
                }
            }

            @Override public void lastOption(final String optionName, final List<String> allValues) {
                final ParsedOptionSpecification optionSpecification =
                        m_specification.getSpecification(optionName);

                if(m_specification.hasUnparsedSpecification())
                {
                    final int maximumArgumentConsumption = Math.min(allValues.size(), optionSpecification.maximumArgumentConsumption());
                    m_validatedUnparsedArguments.addAll(0, allValues.subList(maximumArgumentConsumption, allValues.size()));

                    option(optionName, new ArrayList<String>(allValues.subList(0, maximumArgumentConsumption)));
                }
                else
                {
                    option(optionName, allValues);
                }
            }

            @Override public void unparsed(final List<String> values) {
                for (final ParsedOptionSpecification mandatoryOptionSpecification : m_specification.getMandatoryOptions())
                {
                    if (!arguments.containsAny(mandatoryOptionSpecification.getNames()))
                    {
                        m_validationErrorBuilder.missingOption(mandatoryOptionSpecification);
                    }
                }

                m_validatedUnparsedArguments.addAll(values);
                validateUnparsedOptions();
                m_validationErrorBuilder.validate();
            }

            private void validateUnparsedOptions()
            {
                if (m_specification.hasUnparsedSpecification())
                {
                    final OptionSpecification argumentSpecification = m_specification.getUnparsedSpecification();

                    if(argumentSpecification.isOptional() && m_validatedUnparsedArguments.isEmpty())
                    {
                        // OK
                    }
                    else if(!argumentSpecification.allowedThisManyValues(m_validatedUnparsedArguments.size()))
                    {
                        m_validationErrorBuilder.wrongNumberOfValues(argumentSpecification, m_validatedUnparsedArguments);
                    }
                }
                else if (!m_validatedUnparsedArguments.isEmpty())
                {
                    m_validationErrorBuilder.unexpectedTrailingValue(m_validatedUnparsedArguments);
                }
            }

            private void checkAndAddValues(
                    final ParsedOptionSpecification optionSpecification,
                    final String option,
                    final ArrayList<String> values)
            {
                for (final String value : values)
                {
                    if (!patternMatches(optionSpecification, value))
                    {
                        m_validationErrorBuilder.patternMismatch(optionSpecification, value);
                    }
                }
                m_validatedArguments.put(option, new ArrayList<String>(values));
            }

            private boolean patternMatches(final ParsedOptionSpecification optionSpecification, final String value)
            {
                return value.matches(optionSpecification.getPattern());
            }
        });

        return new ArgumentCollectionImpl(m_validatedArguments, m_validatedUnparsedArguments);
    }
}
