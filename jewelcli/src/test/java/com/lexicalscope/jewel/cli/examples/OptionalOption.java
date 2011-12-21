package com.lexicalscope.jewel.cli.examples;

import com.lexicalscope.jewel.cli.Option;

public interface OptionalOption
{
   @Option
   int getCount();

   boolean isCount();
}
