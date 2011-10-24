package uk.co.flamingpenguin.jewel.cli.examples;

import java.io.File;
import java.util.List;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface FileExample
{
   @Option
   List<File> getSource();
}
