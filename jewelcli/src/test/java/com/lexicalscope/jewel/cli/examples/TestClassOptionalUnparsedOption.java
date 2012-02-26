package com.lexicalscope.jewel.cli.examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static com.lexicalscope.jewel.cli.CliFactory.parseArgumentsUsingInstance;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.jewel.cli.ArgumentValidationException;

public class TestClassOptionalUnparsedOption {
    @Rule public final ExpectedException exception = ExpectedException.none();

    @Test public void testOptionalUnparsedOptionPresent() throws ArgumentValidationException {
        final ClassOptionalUnparsedOption result =
                parseArgumentsUsingInstance(new ClassOptionalUnparsedOption(), new String[] {
                        "--myOptionalUnparsedOption",
                        "3",
                        "7" });

        assertThat(result.getMyOptionalUnparsedOption(), contains(3, 7));
    }

    @Test public void testOptionalUnparsedOptionMissing() throws ArgumentValidationException {
        final ClassOptionalUnparsedOption result =
                parseArgumentsUsingInstance(new ClassOptionalUnparsedOption(), new String[] {});

        assertThat(result.getMyOptionalUnparsedOption(), Matchers.<Integer>empty());
    }
}
