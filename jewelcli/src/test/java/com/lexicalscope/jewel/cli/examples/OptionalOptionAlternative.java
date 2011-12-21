package com.lexicalscope.jewel.cli.examples;

import com.lexicalscope.jewel.cli.Option;

public interface OptionalOptionAlternative
{
    @Option(defaultToNull = true) Integer getCount();
}
