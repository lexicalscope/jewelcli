package com.lexicalscope.jewel.cli;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.lexicalscope.jewel.UtilitiesForTesting;

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

public class CliValidationExceptionMatcher {
    public static Matcher<ArgumentValidationException> validationExceptionWithMessage(final String message) {
        return new TypeSafeMatcher<ArgumentValidationException>() {
            @Override public void describeTo(final Description description) {
                description.appendText(ArgumentValidationException.class.getSimpleName() + " with message ").appendValue(
                        message);
            }

            @Override protected boolean matchesSafely(final ArgumentValidationException item) {
                return item.getMessage().equals(message);
            }
        };
    }

    public static Matcher<ArgumentValidationException> validationExceptionWithMessageLines(final String... message) {
        return validationExceptionWithMessage(UtilitiesForTesting.joinLines(message));
    }
}
