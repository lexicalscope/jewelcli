package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestSimpleExample {
    @Test public void testSimpleExample() throws CliValidationException {
        final SimpleExample result0 = CliFactory.parseArguments(SimpleExample.class, new String[] { "--count", "3" });
        assertEquals(3, result0.getCount());
    }
}
