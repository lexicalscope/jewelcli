package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


class ParsedArgumentsBuilder
{
   private final Map<String, List<String>> arguments = new LinkedHashMap<String, List<String>>();
   private List<String> currentValues;
   private final List<String> unparsed = new ArrayList<String>();

   public void add(final String argument) throws ArgumentValidationException
   {
      if(argument.length() > 1 && argument.startsWith("-"))
      {
         addOption(argument.substring(1, argument.length()).trim());
      }
      else
      {
         addValue(argument);
      }
   }

   private void addValue(final String value) throws ArgumentValidationException
   {
      if(currentValues == null)
      {
         throw new ArgumentValidationException(new ArgumentValidationException.ValidationError(){
            public ErrorType getErrorType()
            {
               return ArgumentValidationException.ValidationError.ErrorType.MisplacedValue;
            }

            public String getMessage()
            {
               return value;
            }

            public OptionSpecification getSpecification()
            {
               return null;
            }});
      }
      else
      {
         currentValues.add(value);
      }
   }

   private void addOption(final String option)
   {
      currentValues = new ArrayList<String>();
      arguments.put(option, currentValues);
   }

   public void setUnparsed(final String[] unparsed)
   {
      this.unparsed.addAll(Arrays.asList(unparsed));
   }

   public ParsedArguments getParsedArguments()
   {
      final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

      for (final Entry<String, List<String>> entry : arguments.entrySet())
      {
         finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
      }

      return new ArgumentsImpl(finalArguments, new ArrayList<String>(unparsed));
   }
}
