package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestOptionalOption {
    @Test public void testOptionalOption() throws ArgumentValidationException {
        final OptionalOption result0 = CliFactory.parseArguments(OptionalOption.class, new String[] { "--count", "3" });
        assertEquals(true, result0.isCount());
        assertEquals(3, result0.getCount());

        final OptionalOption result1 = CliFactory.parseArguments(OptionalOption.class, new String[] {});
        assertEquals(false, result1.isCount());
    }
}
