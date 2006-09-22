package uk.co.flamingpenguin.jewel.cli.examples;

import java.io.File;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestFileExample extends TestCase
{
   public void testFileExample() throws ArgumentValidationException
   {
      final FileExample result0 = CliFactory.parseArguments(FileExample.class, new String[]{"-source", "test.txt", "/etc/passwd"});
      assertEquals(2, result0.getSource().size());
      assertEquals(new File("test.txt"), result0.getSource().get(0));
      assertEquals(new File("/etc/passwd"), result0.getSource().get(1));
   }
}
