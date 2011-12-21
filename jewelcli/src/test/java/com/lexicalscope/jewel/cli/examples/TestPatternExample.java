package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestPatternExample {
    @Test public void testPatternExample() {
        try {
            CliFactory.parseArguments(PatternExample.class, new String[] {
                    "--classes",
                    "java.util.String",
                    "my.invalid..Klass" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertEquals("Cannot match (my.invalid..Klass) to pattern: --classes /(\\w+\\.)*\\w+/...", e.getMessage());
        }
    }
}
