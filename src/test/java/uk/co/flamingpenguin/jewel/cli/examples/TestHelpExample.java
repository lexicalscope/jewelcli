package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.TestUtils;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestHelpExample extends TestCase
{
   public void testHelpExample() throws ArgumentValidationException
   {
      final Cli<HelpExample> cli = CliFactory.createCli(HelpExample.class);

      assertEquals(TestUtils.joinLines("The options available are:",
                                       "\t--count value",
                                       "\t--email /^[^\\S@]+@[\\w.]+$/ : your email address",
                                       "\t--location value : the location of something",
                                       "\t--pattern -p value : a pattern"), cli.getHelpMessage());
   }
}
