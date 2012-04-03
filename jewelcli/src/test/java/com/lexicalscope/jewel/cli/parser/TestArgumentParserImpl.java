package com.lexicalscope.jewel.cli.parser;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class TestArgumentParserImpl {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public ParsedArguments parsedArguments;

    final ArgumentParser impl = new ArgumentParserImpl();

    @Test public void testParseArguments() {
        parseArguments(new String[] {});
    }

    @Test public void testParseArgumentsNotUparsed() {
        context.checking(new Expectations() {{
           oneOf(parsedArguments).addOption("a");
           oneOf(parsedArguments).addValue("1");
           oneOf(parsedArguments).addValue("2");
           oneOf(parsedArguments).addOption("b");
           oneOf(parsedArguments).addOption("c");
           oneOf(parsedArguments).addValue("1");
           oneOf(parsedArguments).addValue("2");
        }});

        parseArguments(new String[] { "-a", "1", "2", "-b", "-c", "1", "2" });
    }

    @Test public void testParseArgumentsUnparsed() {
        context.checking(new Expectations() {{
            oneOf(parsedArguments).addOption("a");
            oneOf(parsedArguments).addValue("1");
            oneOf(parsedArguments).addValue("2");
            oneOf(parsedArguments).addOption("b");
            oneOf(parsedArguments).addOption("c");
            oneOf(parsedArguments).addValue("1");
            oneOf(parsedArguments).addValue("2");
            oneOf(parsedArguments).unparsedOptionsFollow();
            oneOf(parsedArguments).addValue("3");
            oneOf(parsedArguments).addValue("4");
        }});

        parseArguments(new String[] { "-a", "1", "2", "-b", "-c", "1", "2", "--", "3", "4" });
    }

    @Test public void testParseArgumentsOnlyUnparsed() {
        context.checking(new Expectations() {{
            oneOf(parsedArguments).unparsedOptionsFollow();
            oneOf(parsedArguments).addValue("3");
            oneOf(parsedArguments).addValue("4");
        }});

        parseArguments(new String[] { "--", "3", "4" });
    }

    @Test public void testParseArgumentsOnlyUnparsedSeperator() {
        context.checking(new Expectations() {{
            oneOf(parsedArguments).unparsedOptionsFollow();
        }});

        parseArguments(new String[] { "--" });
    }

    @Test public void testParseShortArguments() {
        context.checking(new Expectations() {{
            oneOf(parsedArguments).addOption("a");
            oneOf(parsedArguments).addOption("b");
            oneOf(parsedArguments).addOption("c");
        }});

        parseArguments(new String[] { "-abc" });
    }

    @Test public void testParseAssignedValue() {
        context.checking(new Expectations() {{
            oneOf(parsedArguments).addOption("option");
            oneOf(parsedArguments).addValue("value");
        }});

        parseArguments(new String[] { "--option=value" });
    }

    private void parseArguments(final String[] arguments) {
        impl.parseArguments(parsedArguments, arguments);
    }
}
