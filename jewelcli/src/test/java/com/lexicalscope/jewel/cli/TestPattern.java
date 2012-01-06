package com.lexicalscope.jewel.cli;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestPattern {
    public interface TestStringPattern {
        @Option(pattern = "[a-z]+") String getOption();
    }

    @Test public void testStringPatternMismatch() {
        try {
            CliFactory.parseArguments(TestStringPattern.class, "--option", "ABC");
            fail();
        } catch (final CliValidationException e) {
            final List<OptionValidationException> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(PatternMismatchException.class, validationErrors.get(0).getClass());
            assertEquals("Cannot match (ABC) to pattern: --option /[a-z]+/", validationErrors.get(0).getMessage());
        }
    }

    @Test public void testStringPattern() throws CliValidationException {
        CliFactory.parseArguments(TestStringPattern.class, "--option", "abc");
    }
}
