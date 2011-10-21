package uk.co.flamingpenguin.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class TestMultipleOptionNames {
    @Test public void testMultipleOptionNames() throws ArgumentValidationException {
        final MultipleOptionNames result0 =
                CliFactory.parseArguments(MultipleOptionNames.class, new String[] { "--count", "3" });
        assertEquals(3, result0.getCount());

        final MultipleOptionNames result1 =
                CliFactory.parseArguments(MultipleOptionNames.class, new String[] { "--cardinality", "3" });
        assertEquals(3, result1.getCount());
    }
}
