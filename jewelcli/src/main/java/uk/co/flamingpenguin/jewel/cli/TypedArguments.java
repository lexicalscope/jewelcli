package uk.co.flamingpenguin.jewel.cli;

interface TypedArguments
{
   Object getValue(ArgumentSpecification specification);

   boolean contains(ArgumentSpecification specification);

   Object getUnparsedValue();
}
