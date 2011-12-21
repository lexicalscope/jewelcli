package com.lexicalscope.jewel.cli.examples;

import java.io.File;
import java.util.List;

import com.lexicalscope.jewel.cli.Option;

public interface FileExample
{
   @Option
   List<File> getSource();
}
