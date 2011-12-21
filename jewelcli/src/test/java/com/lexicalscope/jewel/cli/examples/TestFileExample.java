package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestFileExample {
    @Test public void testFileExample() throws CliValidationException {
        final FileExample result0 =
                CliFactory.parseArguments(FileExample.class, new String[] { "--source", "test.txt", "/etc/passwd" });
        assertEquals(2, result0.getSource().size());
        assertEquals(new File("test.txt"), result0.getSource().get(0));
        assertEquals(new File("/etc/passwd"), result0.getSource().get(1));
    }
}
