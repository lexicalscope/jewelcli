package uk.co.flamingpenguin.jewel.cli;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests {@link OptionSpecificationImpl} using a generic parameterized type as
 * interface return type
 * 
 * @author Eduard Weissmann
 */
public class TestOptionSpecificationImplGenericType extends TestCase {
    @CommandLineInterface public interface CliWithGenericReturnType {
        @Option MyGenericType<SomeOtherType> getSome();
    }

    public static class MyGenericType<T> {
        private final String userFriendlyName;

        public MyGenericType(final String userFriendlyName) {
            this.userFriendlyName = userFriendlyName;
        }

        String getUserFriendlyName() {
            return userFriendlyName;
        }
    }

    public static class SomeOtherType {}

    @Test public void testGenericsType() throws ArgumentValidationException {
        final CliWithGenericReturnType parsedArguments = CliFactory.parseArguments(CliWithGenericReturnType.class,
                new String[] { "--some", "none" });

        assertThat(parsedArguments.getSome().getUserFriendlyName(), equalTo("none"));
    }
}