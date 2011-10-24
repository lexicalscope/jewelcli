package uk.co.flamingpenguin.jewel.cli.examples;

import java.util.List;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface ListExample
{
   @Option
   List<Integer> getCount();
}
