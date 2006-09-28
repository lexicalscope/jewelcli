package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestPatternExample extends TestCase
{
   public void testPatternExample()
   {
      try
      {
         CliFactory.parseArguments(PatternExample.class, new String[]{"-classes", "java.util.String", "my.invalid..Klass"});
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals("Cannot match (my.invalid..Klass) to pattern : -classes /(\\w+\\.)*\\w+/...", e.getMessage());
      }
   }
}
