package com.lexicalscope.jewel.cli;

import com.lexicalscope.jewel.cli.specification.OptionSpecification;

interface TypedArguments
{
   /**
    * Get the value associated with a particular option
    *
    * @param specification the specification
    *
    * @return the value
    */
   Object getValue(OptionSpecification specification);

   /**
    * Is here a value associated with a particular option
    *
    * @param specification the specification
    *
    * @return true iff there is a value available
    */
   boolean contains(OptionSpecification specification);

   /**
    * @return the unparsed values if any
    */
   Object getUnparsedValue();

   /**
    * Is an unparsed argument specified
    *
    * @return is an unparsed argument specified
    */
   boolean hasUnparsedValue();
}
