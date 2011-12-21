package com.lexicalscope.jewel.cli.examples;

import java.util.List;

import com.lexicalscope.jewel.cli.Option;

public interface DefaultExample
{
   @Option(defaultValue="3")
   int getCount();

   @Option(defaultValue={"3","4","5"})
   List<Integer> getCountList();
}
