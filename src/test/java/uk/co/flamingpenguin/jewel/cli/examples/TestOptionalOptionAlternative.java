package uk.co.flamingpenguin.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class TestOptionalOptionAlternative {
    @Test public void testOptionalOption() throws ArgumentValidationException {
        final OptionalOptionAlternative result0 =
                CliFactory.parseArguments(OptionalOptionAlternative.class, new String[] { "--count", "3" });
        assertEquals(Integer.valueOf(3), result0.getCount());

        final OptionalOptionAlternative result1 =
                CliFactory.parseArguments(OptionalOptionAlternative.class, new String[] {});
        assertEquals(null, result1.getCount());
    }
}
