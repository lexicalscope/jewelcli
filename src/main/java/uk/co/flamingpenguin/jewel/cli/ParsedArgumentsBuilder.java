package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ParsedArgumentsBuilder implements ArgumentParser
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
   private final List<String> m_unparsed = new ArrayList<String>();

   private ParsingState m_state = ParsingState.Initial;
   private List<String> m_currentValues;

   /**
    * {@inheritDoc}
    */
   public ArgumentCollection parseArguments(final String... arguments) throws ArgumentValidationException
   {
      for (final String argument : arguments)
      {
         add(argument);
      }
      return getParsedArguments();
   }

   /**
    * Add an argument to the set
    *
    * @param argument the argument to parse
    *
    * @throws ArgumentValidationException
    */
   void add(final String argument) throws ArgumentValidationException
   {
      if(startsWithDash(argument) && !m_state.equals(ParsingState.Unparsed))
      {
         if(startsWithDoubleDash(argument))
         {
            if(argument.length() > 2)
            {
               addOptionAndValue(argument);
            }
            else
            {
               changeToUnparsedState();
            }
         }
         else
         {
            addConjoinedOptions(argument.substring(1));
         }
      }
      else
      {
         addValue(argument);
      }
   }

   private void addConjoinedOptions(final String options) throws ArgumentValidationException
   {
      for (int i = 0; i < options.length(); i++)
      {
         addOption(options.substring(i, i+1));
      }
   }

   private void addOptionAndValue(final String argument) throws ArgumentValidationException
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

   /**
    * Obtain the parsed arguments
    *
    * @return the arguments that have been parsed
    */
   ArgumentCollection getParsedArguments()
   {
      final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

      for (final Entry<String, List<String>> entry : m_arguments.entrySet())
      {
         finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
      }

      return new ArgumentsCollectionImpl(finalArguments, new ArrayList<String>(m_unparsed));
   }

   private void changeToUnparsedState()
   {
      m_currentValues = null;
      m_state = ParsingState.Unparsed;
   }

   private boolean startsWithDash(final String argument)
   {
      return argument.length() > 1 && argument.startsWith("-");
   }

   private boolean startsWithDoubleDash(final String argument)
   {
      return argument.startsWith("--");
   }
}
