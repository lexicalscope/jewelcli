package com.lexicalscope.jewel.cli;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Test;

/**
 * Tests {@link OptionSpecificationImpl} using a generic parameterized type as
 * interface return type
 * 
 * @author Eduard Weissmann
 */
public class TestOptionGenericTypes {
    public static class SomeOtherType {}

    public static class MyGenericType<T> {
        private final String stringValue;

        public MyGenericType(final String stringValue) {
            this.stringValue = stringValue;
        }

        String getStringValue() {
            return stringValue;
        }
    }

    public static class MyGenericTypeWithTypeArgument<T> {
        private final String stringValue;
        private final Type type;

        public MyGenericTypeWithTypeArgument(final String stringValue, final Type type) {
            this.stringValue = stringValue;
            this.type = type;
        }

        Type getType() {
            return type;
        }

        String getStringValue() {
            return stringValue;
        }
    }

    public static class MyGenericTypeWithTwoConstructors<T> {
        private final String stringValue;
        private Type type;

        public MyGenericTypeWithTwoConstructors(final String stringValue) {
            this.stringValue = stringValue;
        }

        public MyGenericTypeWithTwoConstructors(final String stringValue, final Type type) {
            this.stringValue = stringValue;
            this.type = type;
        }

        Type getType() {
            return type;
        }

        String getStringValue() {
            return stringValue;
        }
    }

    @CommandLineInterface public interface CliWithGenericReturnType {
        @Option MyGenericType<SomeOtherType> getOptionOne();
        boolean isOptionOne();

        @Option MyGenericTypeWithTypeArgument<SomeOtherType> getOptionTwo();
        boolean isOptionTwo();

        @Option MyGenericTypeWithTwoConstructors<SomeOtherType> getOptionThree();
        boolean isOptionThree();
    }

    @Test public void genericTypesCanBeUsedAsOptionValues() throws CliValidationException {
        final CliWithGenericReturnType parsedArguments = CliFactory.parseArguments(CliWithGenericReturnType.class,
                new String[] { "--optionOne", "my string" });

        assertThat(parsedArguments.getOptionOne().getStringValue(), equalTo("my string"));
    }

    @Test public void genericTypeWithAConstructorTakingTypeIsPassedTheGenericReturnTypeOfTheMethod()
            throws CliValidationException {
        final CliWithGenericReturnType parsedArguments = CliFactory.parseArguments(CliWithGenericReturnType.class,
                new String[] { "--optionTwo", "my string" });

        assertThat(parsedArguments.getOptionTwo().getStringValue(), equalTo("my string"));
        assertThat(
                ((ParameterizedType) parsedArguments.getOptionTwo().getType()).getActualTypeArguments()[0],
                equalTo((Type) SomeOtherType.class));
    }

    @Test public void stringConstructorIsPreferedToConstructorContainingType() throws CliValidationException {
        final CliWithGenericReturnType parsedArguments = CliFactory.parseArguments(CliWithGenericReturnType.class,
                new String[] { "--optionThree", "my string" });

        assertThat(parsedArguments.getOptionThree().getStringValue(), equalTo("my string"));
        assertThat(parsedArguments.getOptionThree().getType(), equalTo(null));
    }
}