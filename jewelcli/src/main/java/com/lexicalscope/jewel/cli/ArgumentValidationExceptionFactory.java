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
    static ValidationFailureImpl createUnexpectedOptionError(final String name)
    {
        return new ValidationFailureUnexpectedOption(new UnexpectedOptionSpecification(name));
    }

    static ValidationFailureImpl createAdditionalValuesError(final OptionSpecification optionSpecification)
    {
        return new ValidationFailureUnexpectedAdditionalValue(optionSpecification);
    }

    static ValidationFailureImpl createMissingValueError(final OptionSpecification optionSpecification)
    {
        return new ValidationFailureMissingValue(optionSpecification);
    }

    static ValidationFailureImpl createUnexpectedValueError(final OptionSpecification optionSpecification)
    {
        return new ValidationFailureUnexpectedValue(optionSpecification);
    }

    static ValidationFailureImpl createUnexpectedTrailingValue() {
        return new ValidationFailureUnexpectedTrailingValue(null, "");
    }

    static ValidationFailureImpl createMissingOptionError(final OptionSpecification optionSpecification)
    {
        return new ValidationFailureMissingOption(optionSpecification);
    }

    static ValidationFailureImpl createInvalidValueForType(
            final OptionSpecification optionSpecification,
            final String message)
    {
        return new ValidationFailureInvalidValueForType(optionSpecification, message);
    }

    static ValidationFailureImpl createUnableToConstructType(
            final OptionSpecification optionSpecification,
            final String message)
    {
        return new ValidationFailureUnableToConstructType(optionSpecification, message);
    }

    static ValidationFailureImpl createPatternMismatch(
            final OptionSpecification optionSpecification,
            final String message)
    {
        return new ValidationFailurePatternMismatch(optionSpecification, message);
    }
}
