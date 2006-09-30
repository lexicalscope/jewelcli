package uk.co.flamingpenguin.jewel.cli.examples;

import java.util.Arrays;
import java.util.List;

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
      final List<String> helpMessage = TestUtils.splitLines(cli.getHelpMessage());

      final List<String> expectedMessage =
                         Arrays.asList("\t--location value : the location of something",
                                       "\t--count value",
                                       "\t--pattern -p value : a pattern",
                                       "\t--email /^[^\\S@]+@[\\w.]+$/ : your email address");

      assertTrue(helpMessage.containsAll(expectedMessage));
      assertTrue(expectedMessage.containsAll(helpMessage));
   }
}
