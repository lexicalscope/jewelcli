package uk.co.flamingpenguin.jewel.cli;

interface TypedArguments
{
   Object getValue(ArgumentMethodSpecification specification);

   boolean contains(ArgumentMethodSpecification specification);

   Object getUnparsedValue();
   
   /**
    * Is an unparsed argument specified
    * 
    * @return is an unparsed argument specified 
    */
   boolean hasUnparsedValue();
}
