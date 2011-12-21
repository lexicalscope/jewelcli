package com.lexicalscope.jewel.cli.examples;

import java.io.File;
import java.util.List;

import com.lexicalscope.jewel.cli.CommandLineInterface;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

@CommandLineInterface(application="rm")
public interface RmExample
{
   @Option(shortName="d", longName="directory", description="unlink FILE, even if it is a non-empty directory (super-user only)")
   boolean isRemoveNonEmptyDirectory();

   @Option(shortName="f", description="ignore nonexistent files, never prompt")
   boolean isForce();

   @Option(shortName="i", description="prompt before any removal")
   boolean isInteractive();

   @Option(shortName={"r", "R"}, description="remove the contents of directories recursively")
   boolean isRecursive();

   @Option(shortName="v", description="explain what is being done")
   boolean isVerbose();

   @Option(description="display this help and exit")
   boolean isHelp();

   @Option(description="output version information and exit")
   boolean isVersion();

   @Unparsed(name="FILE")
   List<File> getFiles();
}
