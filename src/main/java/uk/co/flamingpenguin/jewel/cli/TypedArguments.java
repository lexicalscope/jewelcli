package uk.co.flamingpenguin.jewel.cli;

interface TypedArguments
{
   Object getValue(ArgumentSpecification specification);

   boolean contains(ArgumentSpecification specification);

   Object getUnparsedValue();
   
   /**
    * Is an unparsed argument specified
    * 
    * @return is an unparsed argument specified 
    */
   boolean hasUnparsedValue();
}
