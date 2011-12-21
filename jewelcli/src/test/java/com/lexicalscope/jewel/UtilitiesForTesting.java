package com.lexicalscope.jewel;

import java.util.Arrays;
import java.util.List;

/**
 * Utils to help testing
 *
 * @author Tim Wood
 */
public class UtilitiesForTesting
{
   private final static String lineSeparator = System.getProperty("line.separator");

   /**
    * Join the lines together with the platform separator
    *
    * @param lines The lines to join together
    *
    * @return The lines joined together
    */
   public static String joinLines(final String... lines)
   {
      final StringBuilder result = new StringBuilder();

      String separator = "";
      for (final String line : lines)
      {
         result.append(separator).append(line);
         separator = lineSeparator;
      }
      return result.toString();
   }

   public static List<String> splitLines(final String helpMessage)
   {
      return Arrays.asList(helpMessage.split(lineSeparator));
   }
}
