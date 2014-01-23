package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.lexicalscope.jewel.UtilitiesForTesting;
import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestRmExample {
    @Test public void testRmExample() throws ArgumentValidationException {
        final RmExample result0 = CliFactory.parseArguments(RmExample.class, new String[] { "-vrf", "./" });
        assertTrue(result0.isRecursive());
        assertTrue(result0.isVerbose());
        assertTrue(result0.isForce());
        assertFalse(result0.isHelp());
        assertFalse(result0.isVersion());
        assertFalse(result0.isInteractive());

        assertEquals(1, result0.getFiles().size());
        assertEquals(new File("./"), result0.getFiles().get(0));
    }

    @Test public void testRmExampleHelp() throws ArgumentValidationException {
        final Cli<RmExample> result0 = CliFactory.createCli(RmExample.class);
        assertEquals(
                UtilitiesForTesting.joinLines(
                        "Usage: rm [options] FILE...",
                        "\t[--directory -d] : unlink FILE, even if it is a non-empty directory (super-user only)",
                        "\t[--force -f] : ignore nonexistent files, never prompt",
                        "\t[--help] : display this help and exit",
                        "\t[--interactive -i] : prompt before any removal",
                        "\t[--recursive -r -R] : remove the contents of directories recursively",
                        "\t[--verbose -v] : explain what is being done",
                        "\t[--version] : output version information and exit"), result0.getHelpMessage());
    }
}
