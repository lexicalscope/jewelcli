package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lexicalscope.jewel.UtilitiesForTesting;
import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestHelpExample {
    private static final String HELP_MESSAGE =
            UtilitiesForTesting
                    .joinLines(
                            "The options available are:",
                            "\t--count value",
                            "\t--email /^[^\\S@]+@[\\w.]+$/ : your email address",
                            "\t[--help -h] : display help",
                            "\t--location value : the location of something",
                            "\t--firstLongName --secondLongName -m -n value : many aliases",
                            "\t--pattern -p value : a pattern");

    @Test public void testHelpExample() {
        final Cli<HelpExample> cli = CliFactory.createCli(HelpExample.class);

        assertEquals(HELP_MESSAGE, cli.getHelpMessage());

        try {
            cli.parseArguments("--help");
            fail("Help was requested");
        } catch (final ArgumentValidationException e) {
            assertEquals(HELP_MESSAGE, e.getMessage());
        }
    }
}
