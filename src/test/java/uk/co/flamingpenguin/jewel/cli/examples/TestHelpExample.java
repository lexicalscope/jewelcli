package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.UtilitiesForTesting;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestHelpExample extends TestCase
{
   private static final String HELP_MESSAGE = UtilitiesForTesting.joinLines("The options available are:",
                                                                  "\t--count value",
                                                                  "\t--email /^[^\\S@]+@[\\w.]+$/ : your email address",
                                                                  "\t[--help -h] : display help",
                                                                  "\t--location value : the location of something",
                                                                  "\t--pattern -p value : a pattern");

   public void testHelpExample()
   {
      final Cli<HelpExample> cli = CliFactory.createCli(HelpExample.class);

      assertEquals(HELP_MESSAGE, cli.getHelpMessage());

      try
      {
         cli.parseArguments("--help");
         fail("Help was requested");
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals(HELP_MESSAGE, e.getMessage());
      }
   }
}
