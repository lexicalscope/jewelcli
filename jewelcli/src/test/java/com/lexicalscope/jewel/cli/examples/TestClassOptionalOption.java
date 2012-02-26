package com.lexicalscope.jewel.cli.examples;

import static com.lexicalscope.jewel.cli.ArgumentValidationExceptionMatcher.validationException;
import static com.lexicalscope.jewel.cli.CliFactory.parseArgumentsUsingInstance;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.ValidationFailureType;

public class TestClassOptionalOption {
    @Rule public final ExpectedException exception = ExpectedException.none();

    @Test public void testOptionalOptionPresent() throws ArgumentValidationException {
        final ClassOptionalOption result =
                parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] {
                        "--myMandatoryOption",
                        "3",
                        "--myOptionalOption",
                        "7" });

        assertEquals(3, (int) result.getMyMandatoryOption());
        assertEquals(7, (int) result.getMyOptionalOption());
    }

    @Test public void testOptionalOptionMissing() throws ArgumentValidationException {
        final ClassOptionalOption result =
                parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] {
                        "--myMandatoryOption",
                        "3" });

        assertEquals(3, (int) result.getMyMandatoryOption());
        assertEquals(null, result.getMyOptionalOption());
    }

    @Test public void testMandatoryOptionMissing() throws ArgumentValidationException {
        exception.expect(ArgumentValidationException.class);
        exception.expect(validationException(ValidationFailureType.MissingOption));

        parseArgumentsUsingInstance(new ClassOptionalOption(), new String[] { "--myOptionalOption", "3" });
    }
}
