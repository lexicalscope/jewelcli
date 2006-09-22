package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestOptionalOption extends TestCase
{
   public void testOptionalOption() throws ArgumentValidationException
   {
      final OptionalOption result0 = CliFactory.parseArguments(OptionalOption.class, new String[]{"-count", "3"});
      assertEquals(true, result0.isCount());
      assertEquals(3, result0.getCount());

      final OptionalOption result1 = CliFactory.parseArguments(OptionalOption.class, new String[]{});
      assertEquals(false, result1.isCount());
   }
}
