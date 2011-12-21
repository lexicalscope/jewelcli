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

class ValidationErrorImpl extends OptionValidationException
{
    private static final long serialVersionUID = 8856911526459261072L;

    private final ErrorType errorType;

    public ValidationErrorImpl(final ErrorType errorType, final OptionSpecification specification)
    {
        this(errorType, specification, "");
    }

    public ValidationErrorImpl(
            final ErrorType errorType,
            final OptionSpecification specification,
            final String message)
    {
        super(String.format("%s: %s", errorType.getDescription(message), specification));
        this.errorType = errorType;
    }

    @Override public ErrorType getErrorType()
    {
        return errorType;
    }
}