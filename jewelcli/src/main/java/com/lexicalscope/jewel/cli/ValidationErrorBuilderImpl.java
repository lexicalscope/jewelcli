package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.jewel.cli.specification.OptionSpecification;

public class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
    private final List<ValidationFailure> validationExceptions =
            new ArrayList<ValidationFailure>();

    public void missingValue(final OptionSpecification optionSpecification)
    {
        validationExceptions.add(new ValidationFailureMissingValue(optionSpecification));
    }

    public void wrongNumberOfValues(final OptionSpecification optionSpecification, final List<String> excessValues)
    {
        validationExceptions.add(new ValidationFailureWrongNumberOfValues(optionSpecification, excessValues));
    }

    public void unexpectedOption(final String name)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedOptionError(name));
    }

    public void unexpectedValue(final OptionSpecification optionSpecification, final List<String> values)
    {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedValueError(optionSpecification, values));
    }

    @Override public void unexpectedTrailingValue(final List<String> unparsedArguments) {
        validationExceptions.add(ArgumentValidationExceptionFactory.createUnexpectedTrailingValues(unparsedArguments));
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

    public void validate() throws ArgumentValidationException
    {
        if (validationExceptions.size() > 0)
        {
            throw new ArgumentValidationException(validationExceptions);
        }
    }
}
