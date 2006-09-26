package uk.co.flamingpenguin.jewel.cli;

import junit.framework.TestCase;

public class TestArgumentValidationException extends TestCase
{
   public interface TwoOptions
   {
      @Option
      int getCount0();

      @Option
      int getCount1();
   }

   public interface DescribedOption
   {
      @Option(description = "the count")
      int getCount();
   }

   public void testUnrecognisedAndMissingOption()
   {
      try
      {
          CliFactory.parseArguments(TwoOptions.class, new String[]{"-count0", "3", "-coutn", "5"});
          fail();
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals(joinLines("Option is manditory: -count1 value",
                                "Unexpected Option: coutn : Option not recognised"),
                      e.getMessage());
      }
   }

   public void testMissingOption()
   {
      try
      {
          CliFactory.parseArguments(TwoOptions.class, new String[]{"-count0", "3"});
          fail();
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals("Option is manditory: -count1 value", e.getMessage());
      }
   }

   public void testMultipleMissingOption()
   {
      try
      {
          CliFactory.parseArguments(TwoOptions.class, new String[]{});
          fail();
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals(joinLines("Option is manditory: -count0 value",
                                "Option is manditory: -count1 value"),
                      e.getMessage());
      }
   }

   public void testMissingOptionWithDescription()
   {
      try
      {
          CliFactory.parseArguments(DescribedOption.class, new String[]{});
          fail();
      }
      catch (final ArgumentValidationException e)
      {
         assertEquals("Option is manditory: -count value : the count", e.getMessage());
      }
   }

   private static String joinLines(final String... lines)
   {
      final StringBuilder result = new StringBuilder();

      String separator = "";
      for (final String line : lines)
      {
         result.append(separator).append(line);
         separator = System.getProperty("line.separator");
      }
      return result.toString();
   }
}
