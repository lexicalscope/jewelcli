package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

interface ValidatedArguments extends Arguments
{
   List<String> getValues(final String... options);
}
