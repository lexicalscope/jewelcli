package com.lexicalscope.jewel.cli;


/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

class ArgumentValidationExceptionFactory {
    static OptionValidationException createUnexpectedOptionError(final String name)
    {
        return new ValidationErrorImpl(ErrorType.UnexpectedOption, new UnexpectedOptionSpecification(name));
    }

    static OptionValidationException createAdditionalValuesError(final OptionSpecification optionSpecification)
    {
        return new ValidationErrorImpl(ErrorType.AdditionalValue, optionSpecification);
    }

    static OptionValidationException createMissingValueError(final OptionSpecification optionSpecification)
    {
        return new ValidationErrorImpl(ErrorType.MissingValue, optionSpecification);
    }

    static OptionValidationException createUnexpectedValueError(final OptionSpecification optionSpecification)
    {
        return new ValidationErrorImpl(ErrorType.UnexpectedValue, optionSpecification);
    }

    static OptionValidationException createUnexpectedTrailingValue() {
        return new ValidationErrorImpl(ErrorType.UnexpectedTrailingValue, null);
    }

    static OptionValidationException createMissingOptionError(final OptionSpecification optionSpecification)
    {
        return new ValidationErrorImpl(ErrorType.MissingOption, optionSpecification);
    }

    static OptionValidationException createInvalidValueForType(final OptionSpecification optionSpecification, final String message)
    {
        return new ValidationErrorImpl(ErrorType.InvalidValueForType, optionSpecification, message);
    }

    static OptionValidationException createUnableToConstructType(
            final OptionSpecification optionSpecification,
            final String message)
    {
        return new ValidationErrorImpl(ErrorType.UnableToConstructType, optionSpecification, message);
    }

    static OptionValidationException createPatternMismatch(final OptionSpecification optionSpecification, final String message)
    {
        return new ValidationErrorImpl(ErrorType.PatternMismatch, optionSpecification, message);
    }

    static OptionValidationException createhelpRequested(final OptionsSpecification<?> specification)
    {
        return new HelpValidationErrorImpl(specification);
    }
}
