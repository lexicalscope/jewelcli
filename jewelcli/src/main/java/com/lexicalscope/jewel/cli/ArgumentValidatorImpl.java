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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        m_validatedUnparsedArguments.addAll(arguments.getUnparsed());

        final Iterator<Argument> argumentsIterator = arguments.iterator();
        while (argumentsIterator.hasNext())
        {
            final Argument argument = argumentsIterator.next();
            final boolean isLast = !argumentsIterator.hasNext();

            if (!m_specification.isSpecified(argument.getOptionName()))
            {
                m_validationErrorBuilder.unexpectedOption(argument.getOptionName());
            }
            else
            {
                final ParsedOptionSpecification optionSpecification =
                        m_specification.getSpecification(argument.getOptionName());
                if (optionSpecification.isHelpOption())
                {
                    throw new HelpRequestedException(m_specification);
                }
                else if (argument.getValues().size() == 0
                        && optionSpecification.hasValue()
                        && !optionSpecification.isMultiValued())
                {
                    m_validationErrorBuilder.missingValue(optionSpecification);
                }
                else if (!isLast && argument.getValues().size() > 0 && !optionSpecification.hasValue())
                {
                    m_validationErrorBuilder.unexpectedValue(optionSpecification, argument.getValues());
                }
                //else if (!isLast && !optionSpecification.allowedThisManyValues(argument.getValues().size()))
                else if (!isLast && argument.getValues().size() > 1 && !optionSpecification.isMultiValued())
                {
                    m_validationErrorBuilder.unexpectedAdditionalValues(optionSpecification, argument.getValues().subList(1, argument.getValues().size()));
                }

                if (isLast && hasExcessValues(argument, optionSpecification))
                {
                    final List<String> values = new ArrayList<String>();
                    final List<String> unparsed;
                    if (optionSpecification.hasValue())
                    {
                        values.add(argument.getValues().get(0));
                        unparsed = new ArrayList<String>(argument.getValues().subList(1, argument.getValues().size()));
                    }
                    else
                    {
                        unparsed = argument.getValues();
                    }

                    m_validatedArguments.put(argument.getOptionName(), values);
                    m_validatedUnparsedArguments.addAll(0, unparsed);
                }
                else
                {
                    checkAndAddValues(
                            optionSpecification,
                            argument.getOptionName(),
                            new ArrayList<String>(argument.getValues()));
                }
            }
        }

        for (final ParsedOptionSpecification optionSpecification : m_specification.getMandatoryOptions())
        {
            if (!arguments.containsAny(optionSpecification.getNames()))
            {
                m_validationErrorBuilder.missingOption(optionSpecification);
            }
        }

        validateUnparsedOptions();

        m_validationErrorBuilder.validate();

        return new ArgumentCollectionImpl(m_validatedArguments, m_validatedUnparsedArguments);
    }

    private void validateUnparsedOptions()
    {
        if (m_specification.hasUnparsedSpecification())
        {
            final OptionSpecification argumentSpecification = m_specification.getUnparsedSpecification();

            if (!argumentSpecification.isMultiValued()
                    && !argumentSpecification.isOptional()
                    && m_validatedUnparsedArguments.isEmpty())
            {
                m_validationErrorBuilder.missingValue(argumentSpecification);
            }
            else if (!argumentSpecification.isMultiValued() && m_validatedUnparsedArguments.size() > 1)
            {
                m_validationErrorBuilder.unexpectedAdditionalValues(argumentSpecification, m_validatedUnparsedArguments.subList(1, m_validatedUnparsedArguments.size()));
            }
        }
        else if (!m_validatedUnparsedArguments.isEmpty())
        {
            m_validationErrorBuilder.unexpectedTrailingValue(m_validatedUnparsedArguments);
        }
    }

    private boolean hasExcessValues(final Argument entry, final ParsedOptionSpecification optionSpecification)
    {
        return !optionSpecification.isMultiValued()
                && (entry.getValues().size() > 1 || entry.getValues().size() > 0 && !optionSpecification.hasValue());
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
}
