package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface OptionalOption
{
   @Option
   int getCount();

   boolean isCount();
}
