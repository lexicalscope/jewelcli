package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestSimpleExample extends TestCase
{
   public void testSimpleExample() throws ArgumentValidationException
   {
      final SimpleExample result0 = CliFactory.parseArguments(SimpleExample.class, new String[]{"-count", "3"});
      assertEquals(3, result0.getCount());
   }
}
