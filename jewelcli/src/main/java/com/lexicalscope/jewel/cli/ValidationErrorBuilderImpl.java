package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
    private final List<OptionValidationException> m_validationException =
            new ArrayList<OptionValidationException>();

    public void missingValue(final OptionSpecification optionSpecification)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createMissingValueError(optionSpecification));
    }

    public void unexpectedAdditionalValues(final OptionSpecification optionSpecification)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createAdditionalValuesError(optionSpecification));
    }

    public void unexpectedOption(final String name)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createUnexpectedOptionError(name));
    }

    public void unexpectedValue(final OptionSpecification optionSpecification)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createUnexpectedValueError(optionSpecification));
    }

    @Override public void unexpectedTrailingValue(final List<String> unparsedArguments) {
        m_validationException.add(ArgumentValidationExceptionFactory.createUnexpectedTrailingValue());
    }

    public void missingOption(final OptionSpecification optionSpecification)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createMissingOptionError(optionSpecification));
    }

    public void invalidValueForType(final OptionSpecification optionSpecification, final String message)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createInvalidValueForType(
                optionSpecification,
                message));
    }

    public void unableToConstructType(final OptionSpecification optionSpecification, final String message)
    {
        m_validationException
                .add(ArgumentValidationExceptionFactory.createUnableToConstructType(optionSpecification, message));
    }

    public void patternMismatch(final OptionSpecification optionSpecification, final String value)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createPatternMismatch(optionSpecification, value));
    }

    public void helpRequested(final OptionsSpecification<?> specification)
    {
        m_validationException.add(ArgumentValidationExceptionFactory.createhelpRequested(specification));
    }

    public void validate() throws CliValidationException
    {
        if (m_validationException.size() > 0)
        {
            for (final OptionValidationException error : m_validationException)
            {
                if (ErrorType.HelpRequested.equals(error.getErrorType()))
                {
                    throw new CliValidationException(Arrays.asList(error));
                }
            }

            throw new CliValidationException(m_validationException);
        }
    }
}
