package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.fail;

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
            assertThat(e.getValidationFailures(), contains(validationError(
                    ValidationFailureType.PatternMismatch,
                    "Cannot match (ABC) to pattern: --option /[a-z]+/")));
        }
    }

    @Test public void testStringPattern() throws CliValidationException {
        CliFactory.parseArguments(TestStringPattern.class, "--option", "abc");
    }
}
