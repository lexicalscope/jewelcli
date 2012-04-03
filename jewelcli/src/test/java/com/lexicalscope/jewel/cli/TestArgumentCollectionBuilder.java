package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestArgumentCollectionBuilder {
    private final ArgumentCollectionBuilder argumentCollectionBuilder = new ArgumentCollectionBuilder();

    private ArgumentCollection parsed() {
        return argumentCollectionBuilder.getParsedArguments();
    }

    private boolean containsAny(final String string) {
        return parsed().containsAny(asList(string));
    }

    @Test public void testParseArguments() throws ArgumentValidationException {
        assertEquals(0, parsed().getUnparsed().size());
    }

    @Test public void testParseArgumentsNotUparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.addOption("a");
        argumentCollectionBuilder.addOption("b");
        argumentCollectionBuilder.addOption("c");

        assertTrue(containsAny("a"));
        assertTrue(containsAny("b"));
        assertTrue(containsAny("c"));

        assertEquals(0, parsed().getUnparsed().size());
    }

    @Test public void noOptionsProducesUnparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.addValue("1");
        argumentCollectionBuilder.addValue("2");
        argumentCollectionBuilder.addValue("3");

        assertEquals(3, parsed().getUnparsed().size());
        assertEquals("1", parsed().getUnparsed().get(0));
        assertEquals("2", parsed().getUnparsed().get(1));
        assertEquals("3", parsed().getUnparsed().get(2));
    }

    @Test public void testParseArgumentsUnparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.unparsedOptionsFollow();
        argumentCollectionBuilder.addValue("3");
        argumentCollectionBuilder.addValue("4");

        assertEquals(2, parsed().getUnparsed().size());
        assertEquals("3", parsed().getUnparsed().get(0));
        assertEquals("4", parsed().getUnparsed().get(1));
    }

    @Test public void testParseArgumentsOnlyUnparsedSeperator() throws ArgumentValidationException {
        argumentCollectionBuilder.unparsedOptionsFollow();

        assertEquals(0, parsed().getUnparsed().size());
    }

    @Test public void testParseArgumentsMisplacedValue() {
        try {
            argumentCollectionBuilder.addValue("a");
            argumentCollectionBuilder.addOption("b");
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.MisplacedOption)));
        }
    }
}
