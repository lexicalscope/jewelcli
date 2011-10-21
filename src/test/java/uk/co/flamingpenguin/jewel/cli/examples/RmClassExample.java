package uk.co.flamingpenguin.jewel.cli.examples;

import java.io.File;
import java.util.List;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Option;
import uk.co.flamingpenguin.jewel.cli.Unparsed;

@CommandLineInterface(application = "rm") public class RmClassExample
{
    @Option(shortName = "d", longName = "directory", description = "unlink FILE, even if it is a non-empty directory (super-user only)") void setRemoveNonEmptyDirectory(
            final boolean directory) {}

    @Option(shortName = "f", description = "ignore nonexistent files, never prompt") void setForce(final boolean force) {}

    @Option(shortName = "i", description = "prompt before any removal") void setInteractive(final boolean interactive) {}

    @Option(shortName = { "r", "R" }, description = "remove the contents of directories recursively") void setRecursive(
            final boolean recursive) {}

    @Option(shortName = "v", description = "explain what is being done") void setVerbose(final boolean verbose) {}

    @Option(description = "display this help and exit") void setHelp(final boolean help) {}

    @Option(description = "output version information and exit") void setVersion(final boolean version) {}

    @Unparsed(name = "FILE") void setFiles(final List<File> files) {}

    public boolean isRecursive() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isVerbose() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isForce() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isHelp() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isVersion() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isInteractive() {
        // TODO Auto-generated method stub
        return false;
    }

    public List<File> getFiles() {
        // TODO Auto-generated method stub
        return null;
    }
}
