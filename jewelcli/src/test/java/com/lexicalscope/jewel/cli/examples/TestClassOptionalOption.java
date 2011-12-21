package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;
import static com.lexicalscope.jewel.cli.ArgumentValidationExceptionMatcher.validationException;
import static com.lexicalscope.jewel.cli.CliFactory.parseArgumentsUsingInstance;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.ErrorType;

public class TestClassOptionalOption {
    @Rule public final ExpectedException exception = ExpectedException.none();

    @Test public void testOptionalOptionPresent() throws CliValidationException {
        final ClassOptionalOption result =
                parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] {
                        "--myMandatoryOption",
                        "3",
                        "--myOptionalOption",
                        "7" });

        assertEquals(3, (int) result.getMyMandatoryOption());
        assertEquals(7, (int) result.getMyOptionalOption());
    }

    @Test public void testOptionalOptionMissing() throws CliValidationException {
        final ClassOptionalOption result =
                parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] {
                        "--myMandatoryOption",
                        "3" });

        assertEquals(3, (int) result.getMyMandatoryOption());
        assertEquals(null, result.getMyOptionalOption());
    }

    @Test public void testMandatoryOptionMissing() throws CliValidationException {
        exception.expect(CliValidationException.class);
        exception.expect(validationException(ErrorType.MissingOption));

        parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] { "--myOptionalOption", "3" });
    }
}
