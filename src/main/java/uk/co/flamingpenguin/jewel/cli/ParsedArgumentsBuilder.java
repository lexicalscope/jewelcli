package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ParsedArgumentsBuilder
{
   private final Map<String, List<String>> m_arguments = new LinkedHashMap<String, List<String>>();
   private List<String> m_currentValues;
   private final List<String> m_unparsed = new ArrayList<String>();
   private boolean m_moreOptionsExpected = true;

   public void add(final String argument) throws ArgumentValidationException
   {
      if(argument.length() > 1 && argument.startsWith("-"))
      {
         if(argument.length() > 2 && argument.startsWith("--"))
         {
            if(argument.contains("="))
            {
               final int separatorIndex = argument.indexOf("=");
               addOption(argument.substring(2, separatorIndex).trim());

               if(argument.length() > (separatorIndex + 1))
               {
                  addValue(argument.substring(separatorIndex + 1).trim());
               }
            }
            else
            {
               addOption(argument.substring(2, argument.length()).trim());
            }
         }
         else
         {
            for (int i = 1; i < argument.length(); i++)
            {
               addOption(argument.substring(i, i+1));
            }
         }
      }
      else
      {
         addValue(argument);
      }
   }

   private void addValue(final String value)
   {
      if(m_currentValues == null)
      {
         m_moreOptionsExpected = false;
         m_unparsed.add(value);
      }
      else
      {
         m_currentValues.add(value);
      }
   }

   private void addOption(final String option) throws ArgumentValidationException
   {
      if(m_moreOptionsExpected)
      {
         m_currentValues = new ArrayList<String>();
         m_arguments.put(option, m_currentValues);
      }
      else
      {
          throw new ArgumentValidationException(new ArgumentValidationException.ValidationError(){
          public ErrorType getErrorType()
          {
             return ArgumentValidationException.ValidationError.ErrorType.MisplacedOption;
          }

          public String getMessage()
          {
             return option;
          }

          @Override
          public String toString()
          {
             return String.format("Option not expected in this position: %s", getMessage());
          }});
      }
   }

   public void setUnparsed(final String[] unparsed)
   {
      this.m_unparsed.addAll(Arrays.asList(unparsed));
   }

   public ArgumentCollection getParsedArguments()
   {
      final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

      for (final Entry<String, List<String>> entry : m_arguments.entrySet())
      {
         finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
      }

      return new ArgumentsImpl(finalArguments, new ArrayList<String>(m_unparsed));
   }
}
