package com.lexicalscope.jewel.cli.examples;

import java.io.File;
import java.util.List;

import com.lexicalscope.jewel.cli.CommandLineInterface;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

@CommandLineInterface(application = "rm") public class RmClassExample
{
    private boolean recursive;
    private boolean verbose;
    private boolean version;
    private List<File> files;
    private boolean force;
    private boolean interactive;
    private boolean help;
    private boolean directory;

    @Option(shortName = "d", longName = "directory", description = "unlink FILE, even if it is a non-empty directory (super-user only)") void setRemoveNonEmptyDirectory(
            final boolean directory) {
        this.directory = directory;
    }

    public boolean isRemoveNonEmptyDirectory()
    {
        return directory;
    }

    @Option(shortName = "f", description = "ignore nonexistent files, never prompt") void setForce(final boolean force) {
        this.force = force;
    }

    public boolean isForce() {
        return force;
    }

    @Option(shortName = "i", description = "prompt before any removal") void setInteractive(final boolean interactive) {
        this.interactive = interactive;
    }

    public boolean isInteractive() {
        return interactive;
    }

    @Option(shortName = { "r", "R" }, description = "remove the contents of directories recursively") void setRecursive(
            final boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isRecursive() {
        return recursive;
    }

    @Option(description = "display this help and exit") void setHelp(final boolean help) {
        this.help = help;
    }

    public boolean isHelp() {
        return help;
    }

    @Option(description = "output version information and exit") void setVersion(final boolean version) {
        this.version = version;
    }

    public boolean isVersion() {
        return version;
    }

    @Option(shortName = "v", description = "explain what is being done") void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }

    @Unparsed(name = "FILE") void setFiles(final List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }
}
