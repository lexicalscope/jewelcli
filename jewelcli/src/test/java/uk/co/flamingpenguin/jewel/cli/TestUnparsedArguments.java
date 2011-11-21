package uk.co.flamingpenguin.jewel.cli;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static uk.co.flamingpenguin.jewel.cli.ArgumentValidationExceptionMatcher.validationException;
import static uk.co.flamingpenguin.jewel.cli.CliFactory.parseArguments;

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

    @Test public void testUnparsedOption() throws ArgumentValidationException {
        assertEquals("value", parseArguments(UnparsedOption.class, "value").getName());
    }

    @Test public void testUnparsedOptionMissingValue() throws ArgumentValidationException {
        exception.expect(ArgumentValidationException.class);
        exception.expect(validationException(ArgumentValidationException.ValidationError.ErrorType.MissingValue));

        parseArguments(UnparsedOption.class);
    }

    @Test public void testUnparsedListOption() throws ArgumentValidationException {
        assertThat(
                parseArguments(UnparsedListOption.class, "value0", "value1").getNames(),
                contains("value0", "value1"));
    }

    @Test public void testUnparsedListOptionMissingValue() throws ArgumentValidationException {
        parseArguments(UnparsedListOption.class);
    }

    @Test public void ifNoUnparsedOptionIsSpecifiedButValuesArePresentThenAValidationErrorOccurs()
            throws ArgumentValidationException {
        exception.expect(ArgumentValidationException.class);
        exception
                .expect(validationException(ArgumentValidationException.ValidationError.ErrorType.UnexpectedTrailingValue));

        parseArguments(NoUnparsedOption.class, "value0");
    }

    @Test public void testOptionalUnparsedOption() throws ArgumentValidationException {
        assertEquals(parseArguments(UnparsedOption.class, "value").getName(), "value");
    }

    @Test public void testOptionalUnparsedOptionMissingValue() throws ArgumentValidationException {
        assertFalse(parseArguments(OptionalUnparsedOption.class).isName());
    }

    @Test public void testOptionalUnparsedListOption() throws ArgumentValidationException {
        final OptionalUnparsedListOption result = parseArguments(
                OptionalUnparsedListOption.class,
                "value0",
                "value1");
        assertThat(result.getNames(), contains("value0", "value1"));
        assertTrue(result.isNames());
    }

    @Test public void testOptionalUnparsedListOptionMissingValue() throws ArgumentValidationException {
        final OptionalUnparsedListOption result = parseArguments(OptionalUnparsedListOption.class);
        assertFalse(result.isNames());
        assertThat(result.getNames(), nullValue());
    }

    @Test public void unparsedListOptionMissingValueDefaultsToEmpty() throws ArgumentValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToEmpty.class).getNames().size(), equalTo(0));
    }

    @Test public void unparsedListOptionMissingValueDefaultsToNull() throws ArgumentValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToNull.class).getNames(), nullValue());
    }

    @Test public void unparsedListOptionMissingValueDefaultsToValues() throws ArgumentValidationException {
        assertThat(parseArguments(UnparsedListOptionDefaultToValues.class).getNames(), contains("value0", "value1"));
    }
}
