package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

interface OptionSpecification extends ArgumentSpecification
{
   List<String> getShortNames();

   String getLongName();

   String getDescription();

   boolean hasValue();

   boolean hasShortName();

   boolean isOptional();

   StringBuilder getSummary(StringBuilder result);

   boolean patternMatches(String value);

   List<String> getAllNames();
   
   boolean hasDefaultValue();
   
   List<String> getDefaultValue();
}