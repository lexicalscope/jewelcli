package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestDefaultExample {
    @Test public void testDefaultExample() throws CliValidationException {
        final DefaultExample result0 = CliFactory.parseArguments(DefaultExample.class, new String[] {});
        assertEquals(3, result0.getCount());
        assertEquals(3, (int) result0.getCountList().get(0));
        assertEquals(4, (int) result0.getCountList().get(1));
        assertEquals(5, (int) result0.getCountList().get(2));
    }

    @Test public void testDefaultExampleSpecified() throws CliValidationException {
        final DefaultExample result0 =
                CliFactory.parseArguments(DefaultExample.class, new String[] {
                        "--count",
                        "4",
                        "--countList",
                        "0",
                        "1",
                        "2" });
        assertEquals(4, result0.getCount());
        assertEquals(0, (int) result0.getCountList().get(0));
        assertEquals(1, (int) result0.getCountList().get(1));
        assertEquals(2, (int) result0.getCountList().get(2));
    }
}
