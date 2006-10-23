package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestDefaultExample extends TestCase
{
   public void testDefaultExample() throws ArgumentValidationException
   {
      final DefaultExample result0 = CliFactory.parseArguments(DefaultExample.class, new String[]{});
      assertEquals(3, result0.getCount());
      assertEquals(3, (int) result0.getCountList().get(0));
      assertEquals(4, (int) result0.getCountList().get(1));
      assertEquals(5, (int) result0.getCountList().get(2));
   }

   public void testDefaultExampleSpecified() throws ArgumentValidationException
   {
      final DefaultExample result0 = CliFactory.parseArguments(DefaultExample.class, new String[]{"--count", "4", "--countList", "0", "1", "2"});
      assertEquals(4, result0.getCount());
      assertEquals(0, (int) result0.getCountList().get(0));
      assertEquals(1, (int) result0.getCountList().get(1));
      assertEquals(2, (int) result0.getCountList().get(2));
   }
}
