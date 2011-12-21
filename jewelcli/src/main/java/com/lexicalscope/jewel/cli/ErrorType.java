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

public enum ErrorType
{
    UnexpectedOption
    {
        @Override public String getDescription(final String message)
        {
            return CliValidationException.m_messages.getString("validationError.UnexpectedOption");
        }
    },
    MissingValue
    {
        @Override public String getDescription(final String message)
        {
            return CliValidationException.m_messages.getString("validationError.MissingValue");
        }
    },
    MisplacedOption
    {
        @Override public String getDescription(final String message)
        {
            return String.format(
                    CliValidationException.m_messages.getString("validationError.MisplacedOption"),
                    message);
        }
    },
    UnexpectedValue
    {
        @Override public String getDescription(final String message)
        {
            return CliValidationException.m_messages.getString("validationError.UnexpectedValue");
        }
    },
    AdditionalValue
    {
        @Override public String getDescription(final String message)
        {
            return CliValidationException.m_messages.getString("validationError.AdditionalValue");
        }
    },
    MissingOption
    {
        @Override public String getDescription(final String message)
        {
            return CliValidationException.m_messages.getString("validationError.MissingOption");
        }
    },
    InvalidValueForType
    {
        @Override public String getDescription(final String message)
        {
            return String.format(
                    CliValidationException.m_messages.getString("validationError.InvalidValueForType"),
                    message);
        }
    },
    UnableToConstructType
    {
        @Override public String getDescription(final String message)
        {
            return String.format(
                    CliValidationException.m_messages.getString("validationError.UnableToConstructType"),
                    message);
        }
    },
    PatternMismatch
    {
        @Override public String getDescription(final String message)
        {
            return String.format(
                    CliValidationException.m_messages.getString("validationError.PatternMismatch"),
                    message);
        }
    },
    HelpRequested
    {
        @Override public String getDescription(final String message)
        {
            return message;
        }
    },
    UnexpectedTrailingValue {
        @Override public String getDescription(final String message) {
            return CliValidationException.m_messages.getString("validationError.UnexpectedTrailingValue");
        }
    };

    public abstract String getDescription(String message);
}