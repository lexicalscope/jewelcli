package com.lexicalscope.jewel.cli;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lexicalscope.jewel.UtilitiesForTesting;

public class TestArgumentValidationException {
    public interface TwoOptions {
        @Option int getCount0();

        @Option int getCount1();
    }

    public interface DescribedOption {
        @Option(description = "the count") int getCount();
    }

    @Test public void testUnrecognisedAndMissingOption() {
        try {
            CliFactory.parseArguments(TwoOptions.class, new String[] { "--count0", "3", "--coutn", "5" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals(UtilitiesForTesting.joinLines("Unexpected Option: coutn : Option not recognised",
                                          "Option is mandatory: --count1 value"),
                      e.getMessage());
        }
    }

    @Test public void testMissingOption() {
        try {
            CliFactory.parseArguments(TwoOptions.class, new String[] { "--count0", "3" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals("Option is mandatory: --count1 value", e.getMessage());
        }
    }

    @Test public void testMissingDashes() {
        try {
            CliFactory.parseArguments(TwoOptions.class, new String[] { "count0", "3", "--count1", "4" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals("Option not expected in this position: count1", e.getMessage());
        }
    }

    @Test public void testMultipleMissingOption() {
        try {
            CliFactory.parseArguments(TwoOptions.class, new String[] {});
            fail();
        } catch (final CliValidationException e) {
            assertEquals(UtilitiesForTesting.joinLines("Option is mandatory: --count0 value",
                                "Option is mandatory: --count1 value"),
                      e.getMessage());
        }
    }

    @Test public void testMissingOptionWithDescription() {
        try {
            CliFactory.parseArguments(DescribedOption.class, new String[] {});
            fail();
        } catch (final CliValidationException e) {
            assertEquals("Option is mandatory: --count value : the count", e.getMessage());
        }
    }
}
