package com.lexicalscope.jewel.cli.examples;

import static com.lexicalscope.jewel.cli.CliFactory.parseArguments;
import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static com.lexicalscope.jewel.cli.ValidationFailureType.HelpRequested;
import static java.lang.String.format;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.HelpRequestedException;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.ValidationFailureType;

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

public class TestValidationFailure {
    public interface HelpArugments {
        @Option(helpRequest = true) boolean getHelp();
    }

    @Test public void testHelpRequested() throws CliValidationException
    {
        try
        {
            parseArguments(HelpArugments.class, "--help");
            fail("exception should have been thrown");
        }
        catch(final HelpRequestedException e)
        {
            final String expectedMessage = format("The options available are:%n\t[--help]");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(HelpRequested, expectedMessage)));
        }
    }

    public interface InvalidValueForType {
        @Option Integer getMyOption();
    }

    @Test public void invalidImageThrowsException() throws CliValidationException
    {
        try
        {
            parseArguments(InvalidValueForType.class, "--myOption", "wrongValue");
            fail("exception should have been thrown");
        }
        catch(final CliValidationException e)
        {
            final String expectedMessage = format("Invalid value (Unsupported number format: For input string: \"wrongValue\"): --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.InvalidValueForType, expectedMessage)));
        }
    }


    /*
    InvalidValueForType,
    MisplacedOption,
    MissingOption,
    MissingValue,
    PatternMismatch,
    UnableToConstructType,
    UnexpectedAdditionalValue,
    UnexpectedOption,
    UnexpectedTrailingValue,
    UnexpectedValue,
     */
}
