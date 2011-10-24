package uk.co.flamingpenguin.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class TestSimpleExample {
    @Test public void testSimpleExample() throws ArgumentValidationException {
        final SimpleExample result0 = CliFactory.parseArguments(SimpleExample.class, new String[] { "--count", "3" });
        assertEquals(3, result0.getCount());
    }
}
