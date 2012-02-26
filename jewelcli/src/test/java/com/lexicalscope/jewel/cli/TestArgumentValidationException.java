package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.CliFactory.parseArguments;
import static com.lexicalscope.jewel.cli.ArgumentValidationExceptionMatcher.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestArgumentValidationException {
    @Rule public ExpectedException exception = ExpectedException.none();

    public interface TwoOptions {
        @Option int getCount0();

        @Option int getCount1();
    }

    public interface DescribedOption {
        @Option(description = "the count") int getCount();
    }

    @Test public void testUnrecognisedAndMissingOption() {
        exception.expect(validationExceptionWithMessageLines(
                "Unexpected Option: coutn",
                "Option is mandatory: --count1 value"));

        parseArguments(TwoOptions.class, new String[] { "--count0", "3", "--coutn", "5" });
    }

    @Test public void testMissingOption() {
        exception.expect(validationExceptionWithMessage("Option is mandatory: --count1 value"));

        parseArguments(TwoOptions.class, new String[] { "--count0", "3" });
    }

    @Test public void testMissingDashes() {
        exception.expect(validationExceptionWithMessage("Option not expected in this position (count1)"));

        parseArguments(TwoOptions.class, new String[] { "count0", "3", "--count1", "4" });
    }

    @Test public void testMultipleMissingOption() {
        exception.expect(validationExceptionWithMessageLines(
                "Option is mandatory: --count0 value",
                "Option is mandatory: --count1 value"));

        parseArguments(TwoOptions.class, new String[] {});
    }

    @Test public void testMissingOptionWithDescription() {
        exception.expect(validationExceptionWithMessage("Option is mandatory: --count value : the count"));

        parseArguments(DescribedOption.class, new String[] {});
    }
}
