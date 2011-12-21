package com.lexicalscope.jewel.cli;

import java.util.List;

interface ValidationErrorBuilder
{
    void unexpectedOption(String name);

    void unexpectedValue(OptionSpecification optionSpecification);

    void missingValue(OptionSpecification optionSpecification);

    void unexpectedAdditionalValues(OptionSpecification optionSpecification);

    void unexpectedTrailingValue(List<String> unparsedArguments);

    void missingOption(OptionSpecification optionSpecification);

    void unableToConstructType(OptionSpecification optionSpecification, String message);

    void invalidValueForType(OptionSpecification optionSpecification, String message);

    void patternMismatch(OptionSpecification optionSpecification, String value);

    void helpRequested(OptionsSpecification<?> specification);

    void validate() throws ArgumentValidationException;
}
