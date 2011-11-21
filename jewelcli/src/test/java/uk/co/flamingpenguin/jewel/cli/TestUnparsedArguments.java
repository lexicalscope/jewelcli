package uk.co.flamingpenguin.jewel.cli;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

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

    @Test public void testUnparsedOption() throws ArgumentValidationException {
        final UnparsedOption result =
                new CliInterfaceImpl<UnparsedOption>(UnparsedOption.class).parseArguments(new String[] { "value" });
        assertEquals("value", result.getName());
    }

    @Test public void testUnparsedOptionMissingValue() {
        try {
            new CliInterfaceImpl<UnparsedOption>(UnparsedOption.class).parseArguments(new String[] {});
            fail();
        } catch (final ArgumentValidationException e) {
            final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(ErrorType.MissingValue, validationErrors.get(0).getErrorType());
        }
    }

    @Test public void testUnparsedListOption() throws ArgumentValidationException {
        final UnparsedListOption result =
                new CliInterfaceImpl<UnparsedListOption>(UnparsedListOption.class).parseArguments(new String[] {
                        "value0",
                        "value1" });
        assertEquals(2, result.getNames().size());
        assertEquals("value0", result.getNames().get(0));
        assertEquals("value1", result.getNames().get(1));
    }

    @Test public void testUnparsedListOptionMissingValue() throws ArgumentValidationException {
        new CliInterfaceImpl<UnparsedListOption>(UnparsedListOption.class).parseArguments(new String[] {});
    }

    @Test public void testOptionalUnparsedOption() throws ArgumentValidationException {
        final UnparsedOption result =
                new CliInterfaceImpl<UnparsedOption>(UnparsedOption.class).parseArguments(new String[] { "value" });
        assertEquals(result.getName(), "value");
    }

    @Test public void testOptionalUnparsedOptionMissingValue() throws ArgumentValidationException {
        final OptionalUnparsedOption result =
                new CliInterfaceImpl<OptionalUnparsedOption>(OptionalUnparsedOption.class)
                        .parseArguments(new String[] {});
        assertFalse(result.isName());
    }

    @Test public void testOptionalUnparsedListOption() throws ArgumentValidationException {
        final OptionalUnparsedListOption result =
                new CliInterfaceImpl<OptionalUnparsedListOption>(OptionalUnparsedListOption.class)
                        .parseArguments(new String[] {
                                "value0",
                                "value1" });
        assertEquals(2, result.getNames().size());
        assertEquals("value0", result.getNames().get(0));
        assertEquals("value1", result.getNames().get(1));
        assertTrue(result.isNames());
    }

    @Test public void testOptionalUnparsedListOptionMissingValue() throws ArgumentValidationException {
        final OptionalUnparsedListOption result =
                new CliInterfaceImpl<OptionalUnparsedListOption>(OptionalUnparsedListOption.class)
                        .parseArguments(new String[] {});
        assertFalse(result.isNames());
    }
}
