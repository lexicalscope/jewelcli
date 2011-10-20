package uk.co.flamingpenguin.jewel.cli;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

public class TestParsedArgumentsBuilder {
    @Test public void testAdd() throws ArgumentValidationException {
        try {
            final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
            parsedArgumentsBuilder.add("a");
            parsedArgumentsBuilder.add("-b");
            fail("rouge option should have been detected");
        } catch (final ArgumentValidationException e) {
            assertEquals(1, e.getValidationErrors().size());
            assertEquals(ErrorType.MisplacedOption, e.getValidationErrors().get(0).getErrorType());
        }

        new ArgumentParserImpl().add("-a");

        final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
        parsedArgumentsBuilder.add("-a");
        parsedArgumentsBuilder.add("v1");
        parsedArgumentsBuilder.add("v2");
        parsedArgumentsBuilder.add("-b");
        parsedArgumentsBuilder.add("-c");
        parsedArgumentsBuilder.add("v1");
        parsedArgumentsBuilder.add("v2");
    }

    @Test public void testGetUnparsed() {
        final ArgumentParserImpl parsedArguments = new ArgumentParserImpl();
        assertEquals(0, parsedArguments.getParsedArguments().getUnparsed().size());
    }

    @Test public void testNoOptions() throws ArgumentValidationException {
        final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
        parsedArgumentsBuilder.add("v0");
        parsedArgumentsBuilder.add("v1");
        parsedArgumentsBuilder.add("v2");
        assertEquals(3, parsedArgumentsBuilder.getParsedArguments().getUnparsed().size());
    }

    @Test public void testEndOfOptions() throws ArgumentValidationException {
        final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
        parsedArgumentsBuilder.add("-a");
        parsedArgumentsBuilder.add("v0");
        parsedArgumentsBuilder.add("--");
        parsedArgumentsBuilder.add("v1");
        parsedArgumentsBuilder.add("v2");

        final List<String> unparsed = parsedArgumentsBuilder.getParsedArguments().getUnparsed();
        assertEquals(2, unparsed.size());
        assertEquals("v1", unparsed.get(0));
        assertEquals("v2", unparsed.get(1));
    }

    @Test public void testMissingInitalSpecifier() throws ArgumentValidationException {
        final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
        parsedArgumentsBuilder.add("v0");
        try {
            parsedArgumentsBuilder.add("-a");
        } catch (final ArgumentValidationException e) {
            Assert.assertEquals(1, e.getValidationErrors().size());
            Assert.assertEquals(ValidationError.ErrorType.MisplacedOption, e
                    .getValidationErrors()
                    .get(0)
                    .getErrorType());
        }
    }

    @Test public void testIterator() throws ArgumentValidationException {
        final ArgumentParserImpl parsedArgumentsBuilder = new ArgumentParserImpl();
        parsedArgumentsBuilder.add("-a");
        parsedArgumentsBuilder.add("v1");
        parsedArgumentsBuilder.add("v2");

        final Iterator<Argument> iterator = parsedArgumentsBuilder.getParsedArguments().iterator();
        assertTrue(iterator.hasNext());

        final Argument argument = iterator.next();
        assertEquals("a", argument.getOptionName());
        assertEquals(2, argument.getValues().size());
        assertEquals("v1", argument.getValues().get(0));
        assertEquals("v2", argument.getValues().get(1));
    }
}
