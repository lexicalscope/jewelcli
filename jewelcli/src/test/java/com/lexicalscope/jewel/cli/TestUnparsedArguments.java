package com.lexicalscope.jewel.cli;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static com.lexicalscope.jewel.cli.ArgumentValidationExceptionMatcher.validationException;
import static com.lexicalscope.jewel.cli.CliFactory.parseArguments;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class TestUnparsedArguments {
    @Rule public final ExpectedException exception = ExpectedException.none();

    public interface UnparsedOption {
        @Unparsed String getName();
    }

    public interface UnparsedListOption {
        @Unparsed List<String> getNames();
    }

    public interface OptionalUnparsedOption {
        @Unparsed String getName();

        boolean isName();
    }

    public interface OptionalUnparsedListOption {
        @Unparsed List<String> getNames();

        boolean isNames();
    }

    public interface UnparsedListOptionDefaultToEmpty {
        @Unparsed(defaultValue = {}) List<String> getNames();
    }

    public interface UnparsedListOptionDefaultToNull {
        @Unparsed(defaultToNull = true) List<String> getNames();
    }

    public interface UnparsedListOptionDefaultToValues {
        @Unparsed(defaultValue = { "value0", "value1" }) List<String> getNames();
    }

    public interface NoUnparsedOption {

    }

    @Test public void testUnparsedOption() throws CliValidationException {
        assertEquals("value", parseArguments(UnparsedOption.class, "value").getName());
    }

    @Test public void testUnparsedOptionMissingValue() throws CliValidationException {
        exception.expect(CliValidationException.class);
        exception.expect(validationException(ErrorType.MissingValue));

        parseArguments(UnparsedOption.class);
    }

    @Test public void testUnparsedListOption() throws CliValidationException {
        assertThat(
                parseArguments(UnparsedListOption.class, "value0", "value1").getNames(),
                contains("value0", "value1"));
    }

    @Test public void testUnparsedListOptionMissingValue() throws CliValidationException {
        parseArguments(UnparsedListOption.class);
    }

    @Test public void ifNoUnparsedOptionIsSpecifiedButValuesArePresentThenAValidationErrorOccurs()
            throws CliValidationException {
        exception.expect(CliValidationException.class);
        exception
                .expect(validationException(ErrorType.UnexpectedTrailingValue));

        parseArguments(NoUnparsedOption.class, "value0");
    }

    @Test public void testOptionalUnparsedOption() throws CliValidationException {
        assertEquals(parseArguments(UnparsedOption.class, "value").getName(), "value");
    }

    @Test public void testOptionalUnparsedOptionMissingValue() throws CliValidationException {
        assertFalse(parseArguments(OptionalUnparsedOption.class).isName());
    }

    @Test public void testOptionalUnparsedListOption() throws CliValidationException {
        final OptionalUnparsedListOption result = parseArguments(
                OptionalUnparsedListOption.class,
                "value0",
                "value1");
        assertThat(result.getNames(), contains("value0", "value1"));
        assertTrue(result.isNames());
    }

    @Test public void testOptionalUnparsedListOptionMissingValue() throws CliValidationException {
        final OptionalUnparsedListOption result = parseArguments(OptionalUnparsedListOption.class);
        assertFalse(result.isNames());
        assertThat(result.getNames(), nullValue());
    }

    @Test public void unparsedListOptionMissingValueDefaultsToEmpty() throws CliValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToEmpty.class).getNames().size(), equalTo(0));
    }

    @Test public void unparsedListOptionMissingValueDefaultsToNull() throws CliValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToNull.class).getNames(), nullValue());
    }

    @Test public void unparsedListOptionMissingValueDefaultsToValues() throws CliValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToValues.class).getNames(), contains("value0", "value1"));
    }
}
