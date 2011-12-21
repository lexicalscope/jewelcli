package com.lexicalscope.jewel.cli.examples;

import java.util.List;

import com.lexicalscope.jewel.cli.Option;

public interface ListExample
{
   @Option
   List<Integer> getCount();
}
