package com.lexicalscope.jewel.cli;

import java.io.Serializable;

import com.lexicalscope.jewel.cli.specification.OptionSpecification;

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

abstract class ValidationFailureImpl implements Serializable, ValidationFailure
{
    private static final long serialVersionUID = 358674581610898076L;
    private final String message;

    public ValidationFailureImpl(final String message) {
        this.message = message;
    }

    public ValidationFailureImpl(
            final OptionSpecification specification,
            final String description)
    {
        this(String.format("%s: %s", description, specification));
    }

    public final String getMessage() {
        return message;
    }

    @Override public final String toString() {
        return message;
    }
}