package com.lexicalscope.jewel.cli;

import java.util.List;

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

class ValidationFailureUnexpectedTrailingValue extends ValidationFailureImpl {
    private static final long serialVersionUID = 4186655034725500358L;

    public ValidationFailureUnexpectedTrailingValue(final List<String> unparsedValues) {
        super(formatMessage(unparsedValues));
    }

    private static String formatMessage(final List<String> unparsedValues) {
        if(unparsedValues.size() > 1)
        {
            return String.format(ArgumentValidationException.m_messages.getString("validationError.UnexpectedTrailingValues"), unparsedValues);
        }
        return String.format(ArgumentValidationException.m_messages.getString("validationError.UnexpectedTrailingValue"), unparsedValues.get(0));
    }

    @Override public ValidationFailureType getFailureType() {
        return ValidationFailureType.UnexpectedTrailingValue;
    }
}