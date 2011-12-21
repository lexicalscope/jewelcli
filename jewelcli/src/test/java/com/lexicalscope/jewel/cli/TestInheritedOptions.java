package com.lexicalscope.jewel.cli;

import org.junit.Test;

public class TestInheritedOptions {
    public interface SuperInterface {
        @Option boolean getSuperOption();
    }

    public interface SubInterface extends SuperInterface {
        @Option boolean getSubOption();
    }

    @Test public void testSubInterface() throws CliValidationException {
        CliFactory.parseArguments(SubInterface.class, new String[] { "--superOption", "--superOption" });
    }
}
