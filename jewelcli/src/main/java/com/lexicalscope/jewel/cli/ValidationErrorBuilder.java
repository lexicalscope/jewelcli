package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.jewel.cli.specification.OptionSpecification;

public interface ValidationErrorBuilder
{
    void unexpectedOption(String name);

    void unexpectedValue(OptionSpecification optionSpecification, List<String> values);

    void missingValue(OptionSpecification optionSpecification);

    void wrongNumberOfValues(OptionSpecification optionSpecification, List<String> values);

    void unexpectedTrailingValue(List<String> unparsedArguments);

    void missingOption(OptionSpecification optionSpecification);

    void unableToConstructType(OptionSpecification optionSpecification, String message);

    void invalidValueForType(OptionSpecification optionSpecification, String message);

    void patternMismatch(OptionSpecification optionSpecification, String value);

    void validate() throws ArgumentValidationException;
}
