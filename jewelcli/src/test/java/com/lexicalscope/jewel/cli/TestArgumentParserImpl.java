package com.lexicalscope.jewel.cli;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestArgumentParserImpl {
    @Test public void testParseArguments() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] {});
        assertEquals(0, parsed.getUnparsed().size());
    }

    @Test public void testParseArgumentsNotUparsed() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] { "-a", "1", "2", "-b", "-c", "1", "2" });
        assertTrue(parsed.containsAny("a"));
        assertTrue(parsed.containsAny("b"));
        assertTrue(parsed.containsAny("c"));
    }

    @Test public void testParseArgumentsUnparsed() throws CliValidationException {
        final ArgumentCollectionImpl parsed =
                parseArguments(new String[] { "-a", "1", "2", "-b", "-c", "1", "2", "--", "3", "4" });
        assertEquals(2, parsed.getUnparsed().size());
        assertEquals("3", parsed.getUnparsed().get(0));
        assertEquals("4", parsed.getUnparsed().get(1));
    }

    @Test public void testParseArgumentsOnlyUnparsed() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] { "--", "3", "4" });
        assertEquals(2, parsed.getUnparsed().size());
    }

    @Test public void testParseArgumentsOnlyUnparsedSeperator() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] { "--" });
        assertEquals(0, parsed.getUnparsed().size());
    }

    @Test public void testParseArgumentsMisplacedValue() {
        try {
            parseArguments(new String[] { "a", "-b" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals(1, e.getValidationFailures().size());
            assertEquals(ValidationFailureMisplacedOption.class, e.getValidationFailures().get(0).getClass());
        }
    }

    @Test public void testParseShortArguments() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] { "-abc" });
        assertEquals(0, parsed.getUnparsed().size());
        assertTrue(parsed.containsAny("a"));
        assertTrue(parsed.containsAny("b"));
        assertTrue(parsed.containsAny("c"));
        assertFalse(parsed.containsAny("abc"));
    }

    @Test public void testParseAssignedValue() throws CliValidationException {
        final ArgumentCollectionImpl parsed = parseArguments(new String[] { "--option=value" });
        assertEquals(0, parsed.getUnparsed().size());
        assertTrue(parsed.containsAny("option"));
        assertEquals("value", parsed.iterator().next().getValues().get(0));
        assertFalse(parsed.containsAny("option=value"));
    }

    private ArgumentCollectionImpl parseArguments(final String[] arguments) throws CliValidationException {
        final ArgumentParser impl = new ArgumentParserImpl();
        return (ArgumentCollectionImpl) impl.parseArguments(arguments);
    }
}
