package com.lexicalscope.jewel.cli.examples;

import java.util.List;

import com.lexicalscope.jewel.cli.Option;

public interface PatternExample
{
   @Option(pattern = "(\\w+\\.)*\\w+")
   List<String> getClasses();
}
