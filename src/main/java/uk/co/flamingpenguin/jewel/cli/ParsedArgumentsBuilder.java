package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ParsedArgumentsBuilder
{
   /**
    * The state of the parser
    *
    * @author t.wood
    */
   private enum ParsingState
   {
      Initial,
      OptionOrValue,
      NoOptions,
      Unparsed;
   }

   private final Map<String, List<String>> m_arguments = new LinkedHashMap<String, List<String>>();
   private List<String> m_currentValues;
   private final List<String> m_unparsed = new ArrayList<String>();

   private ParsingState m_state = ParsingState.Initial;

   public void add(final String argument) throws ArgumentValidationException
   {
      switch (m_state)
      {
         case Initial:
         case OptionOrValue:
         case NoOptions:
            if(argument.length() > 1 && argument.startsWith("-"))
            {
               if(argument.startsWith("--"))
               {
                  if(argument.length() > 2)
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
                     m_currentValues = null;
                     m_state = ParsingState.Unparsed;
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
            break;
         case Unparsed:
            addValue(argument);
      }
   }

   private void addValue(final String value)
   {
      switch (m_state)
      {
         case Initial:
            m_state = ParsingState.NoOptions;
         case NoOptions:
         case Unparsed:
            m_unparsed.add(value);
            break;
         case OptionOrValue:
            m_currentValues.add(value);
            break;
      }
   }

   private void addOption(final String option) throws ArgumentValidationException
   {
      switch (m_state)
      {
         case Initial:
         case OptionOrValue:
            m_state = ParsingState.OptionOrValue;
            m_currentValues = new ArrayList<String>();
            m_arguments.put(option, m_currentValues);
            break;
         case NoOptions:
         case Unparsed:
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

   public ArgumentCollection getParsedArguments()
   {
      final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

      for (final Entry<String, List<String>> entry : m_arguments.entrySet())
      {
         finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
      }

      return new ArgumentsCollectionImpl(finalArguments, new ArrayList<String>(m_unparsed));
   }
}
