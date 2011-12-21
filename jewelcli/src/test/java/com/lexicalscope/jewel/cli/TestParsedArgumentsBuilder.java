package com.lexicalscope.jewel.cli;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException.ValidationError;
import com.lexicalscope.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

public class TestParsedArgumentsBuilder {
    @Test public void testAdd() throws ArgumentValidationException {
        try {
            final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
            parsedArgumentsBuilder.parseArguments("a", "-b");
            fail("rouge option should have been detected");
        } catch (final ArgumentValidationException e) {
            assertEquals(1, e.getValidationErrors().size());
            assertEquals(ErrorType.MisplacedOption, e.getValidationErrors().get(0).getErrorType());
        }

        new ArgumentParserImpl().parseArguments("-a");
        new ArgumentParserImpl().parseArguments("-a", "v1", "v2", "-b", "-c", "v1", "v2");
    }

    @Test public void testGetUnparsed() {
        assertEquals(0, new ArgumentParserImpl().getParsedArguments().getUnparsed().size());
    }

    @Test public void testNoOptions() throws ArgumentValidationException {
        assertEquals(3, new ArgumentParserImpl().parseArguments("v0", "v1", "v2").getUnparsed().size());
    }

    @Test public void testEndOfOptions() throws ArgumentValidationException {
        final List<String> unparsed =
                new ArgumentParserImpl().parseArguments("-a", "v0", "--", "v1", "v2").getUnparsed();
        assertEquals(2, unparsed.size());
        assertEquals("v1", unparsed.get(0));
        assertEquals("v2", unparsed.get(1));
    }

    @Test public void testMissingInitalSpecifier() throws ArgumentValidationException {
        try {
            new ArgumentParserImpl().parseArguments("v0", "-a");
        } catch (final ArgumentValidationException e) {
            assertEquals(1, e.getValidationErrors().size());
            assertEquals(ValidationError.ErrorType.MisplacedOption, e
                    .getValidationErrors()
                    .get(0)
                    .getErrorType());
        }
    }

    @Test public void testIterator() throws ArgumentValidationException {
        final Iterator<Argument> iterator = new ArgumentParserImpl().parseArguments("-a", "v1", "v2").iterator();
        assertTrue(iterator.hasNext());

        final Argument argument = iterator.next();
        assertEquals("a", argument.getOptionName());
        assertEquals(2, argument.getValues().size());
        assertEquals("v1", argument.getValues().get(0));
        assertEquals("v2", argument.getValues().get(1));
    }
}
