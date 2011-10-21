package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface OptionalOptionAlternative
{
    @Option(defaultToNull = true) Integer getCount();
}
