package uk.co.flamingpenguin.jewel.cli;

interface TypedArguments
{
   Object getValue(OptionSpecification specification);

   boolean contains(OptionSpecification specification);
}
