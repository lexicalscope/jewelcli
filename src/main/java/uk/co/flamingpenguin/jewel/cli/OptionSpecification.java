//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

/**
 * Specifies an Option
 *
 * @author t.wood
 */
interface OptionSpecification extends OptionArgumentsSpecification
{
   /**
    * Get all of the short names of this option. Short names are
    * single characters that will by prefixed by the user with "-".
    *
    * @return the short names of this option
    */
   List<String> getShortNames();

   /**
    * Does this option have a Short Name
    *
    * @return true iff the options has a short name
    */
   boolean hasShortName();

   /**
    * Get the long name of this option. Long names are multiple
    * characters that will be prefixed by the user with "--".
    *
    * @return the long name of this option
    */
   String getLongName();

   /**
    * Get the default values which will be used if the option is
    * not present.
    *
    * @return The default values which will be used if the option is
    *         not present
    */
   List<String> getDefaultValue();

   /**
    * Is there a default value to use if this option is not present? Options
    * with a default value are assumed to be optional.
    *
    * @return True iff this option has a default value
    */
   boolean hasDefaultValue();

   /**
    * Get a description of the option
    *
    * @return a description of the option
    */
   String getDescription();

   /**
    * Is this option a request for help
    *
    * @return True iff this option is a request for help
    */
   boolean isHelpOption();
   
   boolean patternMatches(String value);
}
