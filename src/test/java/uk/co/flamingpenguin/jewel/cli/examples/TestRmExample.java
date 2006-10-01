package uk.co.flamingpenguin.jewel.cli.examples;

import java.io.File;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestRmExample extends TestCase
{
   public void testRmExample() throws ArgumentValidationException
   {
      final RmExample result0 = CliFactory.parseArguments(RmExample.class, new String[]{"-vrf", "./"});
      assertTrue(result0.isRecursive());
      assertTrue(result0.isVerbose());
      assertTrue(result0.isForce());
      assertFalse(result0.isHelp());
      assertFalse(result0.isVersion());
      assertFalse(result0.isInteractive());

      assertEquals(1, result0.getFiles().size());
      assertEquals(new File("./"), result0.getFiles().get(0));
   }
}
