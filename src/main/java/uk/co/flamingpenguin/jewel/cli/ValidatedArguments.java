package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

interface ValidatedArguments extends ArgumentCollection
{
   List<String> getValues(final String... options);
}
