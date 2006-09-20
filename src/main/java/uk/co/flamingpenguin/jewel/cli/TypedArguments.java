package uk.co.flamingpenguin.jewel.cli;

public interface TypedArguments
{
   Object getValue(OptionSpecification specification);

   boolean contains(OptionSpecification specification);
}
