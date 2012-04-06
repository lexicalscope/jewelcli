package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.jewel.cli.arguments.ArgumentProcessor;

public class TestArgumentCollectionBuilder {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public ArgumentProcessor argumentProcessor;

    private final ArgumentCollectionBuilder argumentCollectionBuilder = new ArgumentCollectionBuilder();

    @Test public void testParseArguments() throws ArgumentValidationException {
        context.checking(new Expectations() {{
            oneOf(argumentProcessor).finishedProcessing(emptyStringList());
        }});
        argumentCollectionBuilder.processArguments(argumentProcessor);
    }

    @Test public void testParseArgumentsNotUparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.addOption("a");
        argumentCollectionBuilder.addOption("b");
        argumentCollectionBuilder.addOption("c");

        context.checking(new Expectations() {{
            oneOf(argumentProcessor).processOption("a", emptyStringList());
            oneOf(argumentProcessor).processOption("b", emptyStringList());
            oneOf(argumentProcessor).processLastOption("c", emptyStringList());
            oneOf(argumentProcessor).finishedProcessing(emptyStringList());
        }});
        argumentCollectionBuilder.processArguments(argumentProcessor);
    }

    @Test public void noOptionsProducesUnparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.addValue("1");
        argumentCollectionBuilder.addValue("2");
        argumentCollectionBuilder.addValue("3");

        context.checking(new Expectations() {{
            oneOf(argumentProcessor).finishedProcessing(asList("1", "2", "3"));
        }});
        argumentCollectionBuilder.processArguments(argumentProcessor);
    }

    @Test public void testParseArgumentsUnparsed() throws ArgumentValidationException {
        argumentCollectionBuilder.unparsedOptionsFollow();
        argumentCollectionBuilder.addValue("3");
        argumentCollectionBuilder.addValue("4");

        context.checking(new Expectations() {{
            oneOf(argumentProcessor).finishedProcessing(asList("3", "4"));
        }});
        argumentCollectionBuilder.processArguments(argumentProcessor);
    }

    @Test public void testParseArgumentsOnlyUnparsedSeperator() throws ArgumentValidationException {
        argumentCollectionBuilder.unparsedOptionsFollow();

        context.checking(new Expectations() {{
            oneOf(argumentProcessor).finishedProcessing(emptyStringList());
        }});
        argumentCollectionBuilder.processArguments(argumentProcessor);
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

    public static List<String> emptyStringList() {
        return Collections.emptyList();
    }
}
