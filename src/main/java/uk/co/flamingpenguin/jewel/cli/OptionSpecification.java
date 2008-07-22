package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

interface OptionSpecification extends ArgumentSpecification
{
   List<String> getShortNames();

   String getLongName();

   String getDescription();

   boolean hasValue();

   boolean hasShortName();

   StringBuilder getSummary(StringBuilder result);

   boolean patternMatches(String value);

   List<String> getAllNames();

   boolean hasDefaultValue();

   List<String> getDefaultValue();

   /**
    * Is this option a request for help
    *
    * @return True iff this option is a request for help
    */
   boolean isHelpOption();
}