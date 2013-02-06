package com.lexicalscope.jewel.cli.examples;

import static com.lexicalscope.jewel.cli.CliFactory.parseArguments;
import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static com.lexicalscope.jewel.cli.ValidationFailureType.*;
import static java.lang.String.format;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.HelpRequestedException;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

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

    @Test public void testHelpRequested() throws ArgumentValidationException
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

    @Test public void invalidNumberThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(InvalidValueForType.class, "--myOption", "wrongValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Invalid value (Unsupported number format: For input string: \"wrongValue\"): --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(InvalidValueForType, expectedMessage)));
        }
    }

    public interface MisplacedOption {
        @Option Integer getMyOption();
        @Unparsed List<String> getMyUnparsed();
    }

    @Test public void optionAfterUnparsedThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(MisplacedOption.class, "myOutOfPlaceValue", "--myOption", "2");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option not expected in this position (myOption)");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(MisplacedOption, expectedMessage)));
        }
    }

    public interface MissingOption {
        @Option Integer getMyOption();
    }

    @Test public void missingMandatoryOptionThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(MissingOption.class);
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option is mandatory: --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(MissingOption, expectedMessage)));
        }
    }

    public interface MissingValue {
        @Option Integer getMyOption();
    }

    @Test public void missingValueForOptionThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(MissingValue.class, "--myOption");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option must have a value: --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(MissingValue, expectedMessage)));
        }
    }

    public interface MissingValuesInListWithMinimumOfOne {
        @Option(minimum = 1) List<String> getMyOption();
    }

    @Test public void missingValueInListWithMinimumOfOneThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(MissingValuesInListWithMinimumOfOne.class, "--myOption");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option takes at least 1 value; please specify more than []: --myOption value...");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(TooFewValues, expectedMessage)));
        }
    }

    public interface MissingValuesInListWithMinimumOfTwo {
        @Option(minimum = 2) List<String> getMyOption();
    }

    @Test public void missingValueInListWithMinimumOfTwoThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(MissingValuesInListWithMinimumOfTwo.class, "--myOption", "myValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option takes at least 2 values; please specify more than [myValue]: --myOption value...");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(TooFewValues, expectedMessage)));
        }
    }

    public interface PatternMismatch {
        @Option(pattern="\\d+") String getMyOption();
    }

    @Test public void valueThatDoesNotMatchThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(PatternMismatch.class, "--myOption", "myBadValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Cannot match (myBadValue) to pattern: --myOption /\\d+/");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(PatternMismatch, expectedMessage)));
        }
    }

    /*
    UnableToConstructType
     */

    public interface UnexpectedAdditionalValue {
        @Option String getMyOption();
        @Option String getMyOtherOption();
    }

    @Test public void unusedAdditionalValueThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedAdditionalValue.class, "--myOption", "myValue", "myExcessValue", "--myOtherOption", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option only takes one value; cannot use [myExcessValue]: --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedAdditionalValue, expectedMessage)));
        }
    }

    public interface UnexpectedAdditionalValueInListWithMaximumOfOne {
        @Option(maximum = 1) List<String> getMyOption();
        @Option String getMyOtherOption();
    }

    @Test public void unexpectedAdditionalValueInListWithMaximumOfOneThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedAdditionalValueInListWithMaximumOfOne.class, "--myOption", "myValue", "myExcessValue", "--myOtherOption", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option takes at most 1 value; please remove [myExcessValue]: --myOption value...");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(TooManyValues, expectedMessage)));
        }
    }

    public interface UnexpectedAdditionalValueInListWithMaximumOfTwo {
        @Option(maximum = 2) List<String> getMyOption();
        @Option String getMyOtherOption();
    }

    @Test public void unexpectedAdditionalValueInListWithMaximumOfTwoThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedAdditionalValueInListWithMaximumOfTwo.class, "--myOption", "myValue", "myOtherValue", "myExcessValue", "--myOtherOption", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option takes at most 2 values; please remove [myExcessValue]: --myOption value...");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(TooManyValues, expectedMessage)));
        }
    }

    public interface UnexpectedAdditionalValueInListWithExactArity {
        @Option(exactly = 1) List<String> getMyOption();
        @Option String getMyOtherOption();
    }

    @Test public void unexpectedAdditionalValueInListWithExactArityThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedAdditionalValueInListWithExactArity.class, "--myOption", "myValue", "myExcessValue", "--myOtherOption", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option takes exactly 1 value; please remove [myExcessValue]: --myOption value...");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(TooManyValues, expectedMessage)));
        }
    }

    public interface UnexpectedAdditionalValueInListWithExactMultiValuedArity {
       @Option(exactly = 2) List<String> getMyOption();
       @Option String getMyOtherOption();
   }

   @Test public void unexpectedAdditionalValueInListWithExactMultiValuedArityThrowsException() throws ArgumentValidationException
   {
       try
       {
           parseArguments(UnexpectedAdditionalValueInListWithExactMultiValuedArity.class, "--myOption", "myValue", "myOtherValue", "myExcessValue", "--myOtherOption", "anotherValue");
           fail("exception should have been thrown");
       }
       catch(final ArgumentValidationException e)
       {
           final String expectedMessage = format("Option takes exactly 2 values; please remove [myExcessValue]: --myOption value...");
           assertThat(e.getMessage(), equalTo(expectedMessage));
           assertThat(e.getValidationFailures(), contains(validationError(TooManyValues, expectedMessage)));
       }
   }

    public interface UnexpectedAdditionalUnparsedValue {
        @Option Integer getMyOption();
        @Unparsed String getMyUnparsed();
    }

    @Test public void unusedUnparsedValueThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedAdditionalUnparsedValue.class, "--myOption", "myValue", "anotherValue", "myExcessValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option only takes one value; cannot use [myExcessValue]: ARGUMENTS");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedAdditionalValue, expectedMessage)));
        }
    }

    public interface UnexpectedOption {
        @Option String getMyOption();
    }

    @Test public void unexpectedOptionThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedOption.class, "--myOption", "myValue", "--myOtherOption", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Unexpected Option: myOtherOption");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedOption, expectedMessage)));
        }
    }

    public interface UnexpectedTrailingValue {
        @Option String getMyOption();
    }

    @Test public void unexpectedTrailingValueThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedTrailingValue.class, "--myOption", "myValue", "anotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option only takes one value; cannot use [anotherValue]: --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedAdditionalValue, expectedMessage)));
        }
    }

    @Test public void multipleUnexpectedTrailingValuesThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedTrailingValue.class, "--myOption", "myValue", "anotherValue", "yetAnotherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option only takes one value; cannot use [anotherValue, yetAnotherValue]: --myOption value");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedAdditionalValue, expectedMessage)));
        }
    }

    public interface UnexpectedValue {
        @Option boolean getMyOption();
        @Option String getMyOtherOption();
    }

    @Test public void unexpectedValueThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedValue.class, "--myOption", "myValue", "--myOtherOption", "myOtherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option does not take a value; cannot use (myValue): [--myOption]");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedValue, expectedMessage)));
        }
    }

    @Test public void multipleUnexpectedValuesThrowsException() throws ArgumentValidationException
    {
        try
        {
            parseArguments(UnexpectedValue.class, "--myOption", "myRougeValue", "myOtherRougeValue", "--myOtherOption", "myOtherValue");
            fail("exception should have been thrown");
        }
        catch(final ArgumentValidationException e)
        {
            final String expectedMessage = format("Option does not take any values; cannot use [myRougeValue, myOtherRougeValue]: [--myOption]");
            assertThat(e.getMessage(), equalTo(expectedMessage));
            assertThat(e.getValidationFailures(), contains(validationError(UnexpectedValue, expectedMessage)));
        }
    }
}
