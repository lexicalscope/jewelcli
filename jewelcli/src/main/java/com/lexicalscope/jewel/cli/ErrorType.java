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

public abstract class ErrorType
{
    public static class UnexpectedOptionException extends OptionValidationException
    {
        private static final long serialVersionUID = 928801832540765267L;

        public UnexpectedOptionException(final OptionSpecification specification) {
            super(specification, CliValidationException.m_messages.getString("validationError.UnexpectedOption"));
        }
    };

    public static class MissingValueException extends OptionValidationException
    {
        private static final long serialVersionUID = -6121100779973420806L;

        public MissingValueException(final OptionSpecification specification) {
            super(specification, CliValidationException.m_messages.getString("validationError.MissingValue"));
        }
    };

    public static class MisplacedOptionException extends OptionValidationException
    {
        private static final long serialVersionUID = 299432534435955687L;

        public MisplacedOptionException(final String message) {
            super(String.format(
                    CliValidationException.m_messages.getString("validationError.MisplacedOption"),
                    message));
        }
    };

    public static class UnexpectedValueException extends OptionValidationException
    {
        private static final long serialVersionUID = 6824379048527305429L;

        public UnexpectedValueException(final OptionSpecification specification) {
            super(specification, CliValidationException.m_messages.getString("validationError.UnexpectedValue"));
        }
    };

    public static class AdditionalValueException extends OptionValidationException
    {
        private static final long serialVersionUID = -7899339429456035393L;

        public AdditionalValueException(final OptionSpecification specification) {
            super(specification, CliValidationException.m_messages.getString("validationError.AdditionalValue"));
        }
    };

    public static class MissingOptionException extends OptionValidationException
    {
        private static final long serialVersionUID = 3863449901586707461L;

        public MissingOptionException(final OptionSpecification specification) {
            super(specification, CliValidationException.m_messages.getString("validationError.MissingOption"));
        }
    };

    public static class InvalidValueForTypeException extends OptionValidationException
    {
        private static final long serialVersionUID = -1969505329041721032L;

        public InvalidValueForTypeException(final OptionSpecification specification, final String message) {
            super(specification, String.format(
                    CliValidationException.m_messages.getString("validationError.InvalidValueForType"),
                    message));
        }
    };

    public static class UnableToConstructTypeException extends OptionValidationException
    {
        private static final long serialVersionUID = 7263131866771297371L;

        public UnableToConstructTypeException(final OptionSpecification specification, final String message) {
            super(specification, String.format(
                    CliValidationException.m_messages.getString("validationError.UnableToConstructType"),
                    message));
        }
    };

    public static class PatternMismatchException extends OptionValidationException
    {
        private static final long serialVersionUID = -8802957860895277579L;

        public PatternMismatchException(final OptionSpecification specification, final String message) {
            super(specification, String.format(
                    CliValidationException.m_messages.getString("validationError.PatternMismatch"),
                    message));
        }
    };

    public static class UnexpectedTrailingValueException extends OptionValidationException {
        private static final long serialVersionUID = 4186655034725500358L;

        public UnexpectedTrailingValueException(final OptionSpecification specification, final String description) {
            super(specification, CliValidationException.m_messages.getString("validationError.UnexpectedTrailingValue"));
        }
    };
}