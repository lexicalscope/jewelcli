package uk.co.flamingpenguin.jewel.cli;

interface TypedArguments
{
   Object getValue(OptionArgumentsSpecification specification);

   boolean contains(OptionArgumentsSpecification specification);

   Object getUnparsedValue();
   
   /**
    * Is an unparsed argument specified
    * 
    * @return is an unparsed argument specified 
    */
   boolean hasUnparsedValue();
}
