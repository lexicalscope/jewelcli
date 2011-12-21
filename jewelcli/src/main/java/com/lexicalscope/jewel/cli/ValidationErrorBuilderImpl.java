package com.lexicalscope.jewel.cli;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
    private final List<OptionValidationException> validationExceptions =
            new ArrayList<OptionValidationException>();

    public void missingValue(final OptionSpecification optionSpecification)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createMissingValueError(optionSpecification));
    }

    public void unexpectedAdditionalValues(final OptionSpecification optionSpecification)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createAdditionalValuesError(optionSpecification));
    }

    public void unexpectedOption(final String name)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedOptionError(name));
    }

    public void unexpectedValue(final OptionSpecification optionSpecification)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedValueError(optionSpecification));
    }

    @Override public void unexpectedTrailingValue(final List<String> unparsedArguments) {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedTrailingValue());
    }

    public void missingOption(final OptionSpecification optionSpecification)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createMissingOptionError(optionSpecification));
    }

    public void invalidValueForType(final OptionSpecification optionSpecification, final String message)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createInvalidValueForType(
                optionSpecification,
                message));
    }

    public void unableToConstructType(final OptionSpecification optionSpecification, final String message)
    {
        validationExceptions
                .add(ArgumentValidationExceptionFactory.createUnableToConstructType(optionSpecification, message));
    }

    public void patternMismatch(final OptionSpecification optionSpecification, final String value)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createPatternMismatch(optionSpecification, value));
    }

    public void helpRequested(final OptionsSpecification<?> specification)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createhelpRequested(specification));
    }

    public void validate() throws CliValidationException
    {
        if (validationExceptions.size() > 0)
        {
            for (final OptionValidationException error : validationExceptions)
            {
                if (error instanceof HelpValidationErrorImpl)
                {
                    throw new CliValidationException(asList(error));
                }
            }

            throw new CliValidationException(validationExceptions);
        }
    }
}
