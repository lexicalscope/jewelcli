package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestCliImpl {
    public interface SingleOption {
        @Option String getName();
    }

    public interface ListOption {
        @Option List<String> getName();
    }

    public interface CharacterValue {
        @Option Character getName();
    }

    public interface SingleBooleanOption {
        @Option boolean getName0();

        @Option boolean isName1();
    }

    public interface SingleOptionalOption {
        @Option String getName();

        boolean isName();
    }

    public interface IntegerOption {
        @Option Integer getName();
    }

    public interface IntOption {
        @Option int getName();
    }

    public interface SingleOptionWithArgument {
        @Option String getName(String argument);
    }

    public interface SingleOptionMissingAnnotation {
        String getName();
    }

    public enum TestEnum {
        Value0, Value1, Value2;
    }

    public interface EnumDefaultListOption {
        @Option(defaultValue = { "Value0", "Value1" }) List<TestEnum> getName();
    }

    @Test public void testSingleOption() throws ArgumentValidationException {
        final SingleOption option =
                new CliInterfaceImpl<SingleOption>(SingleOption.class)
                .parseArguments(new String[] { "--name", "value" });
        assertEquals(option.getName(), "value");
    }

    @Test public void testSingleBooleanOption() throws ArgumentValidationException {
        final SingleBooleanOption option =
                new CliInterfaceImpl<SingleBooleanOption>(SingleBooleanOption.class)
                .parseArguments(new String[] { "--name1" });
        assertEquals(option.getName0(), false);
        assertEquals(option.isName1(), true);
    }

    @Test public void testIntegerOption() throws ArgumentValidationException {
        final IntegerOption option =
                new CliInterfaceImpl<IntegerOption>(IntegerOption.class)
                .parseArguments(new String[] { "--name", "10" });
        assertEquals(Integer.valueOf(10), option.getName());
    }

    @Test public void testInvalidIntegerOption() {
        try {
            new CliInterfaceImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[] { "--name", "abc" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(
                    ValidationFailureType.InvalidValueForType,
                    "Invalid value (Unsupported number format: For input string: \"abc\"): --name value")));
        }
    }

    @Test public void testInvalidIntOption() {
        try {
            new CliInterfaceImpl<IntOption>(IntOption.class).parseArguments(new String[] { "--name", "abc" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(
                    ValidationFailureType.InvalidValueForType,
                    "Invalid value (Unsupported number format: For input string: \"abc\"): --name value")));
        }
    }

    @Test public void testInvalidOption() {
        try {
            new CliInterfaceImpl<SingleOption>(SingleOption.class)
            .parseArguments(new String[] { "--invalid", "value" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(
                    validationError(ValidationFailureType.UnexpectedOption),
                    validationError(ValidationFailureType.MissingOption)));
        }
    }

    @Test public void testSingleOptionalOption() throws ArgumentValidationException {
        SingleOptionalOption option =
                new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {
                        "--name",
                "value" });
        assertEquals(option.getName(), "value");
        assertTrue(option.isName());

        option = new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {});
        assertFalse(option.isName());
    }

    @Test public void testSingleOptionalOptionRequestMissing() throws ArgumentValidationException {
        final SingleOptionalOption option =
                new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {});

        assertThat(option.getName(), equalTo(null));
    }

    @Test public void testCharacterValue() throws ArgumentValidationException {
        final CharacterValue option =
                new CliInterfaceImpl<CharacterValue>(CharacterValue.class)
                .parseArguments(new String[] { "--name", "a" });
        assertEquals((Character) 'a', option.getName());
    }

    @Test public void testInvalidCharacterValue() {
        try {
            new CliInterfaceImpl<CharacterValue>(CharacterValue.class).parseArguments(new String[] { "--name", "aa" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.InvalidValueForType)));
        }
    }

    @Test public void testMethodWithArguments() throws ArgumentValidationException {
        try {
            new CliInterfaceImpl<SingleOptionWithArgument>(SingleOptionWithArgument.class).parseArguments(new String[] {
                    "--name",
            "value" });
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.UnexpectedOption)));
        }
    }

    @Test public void testMethodWithMissingAnnotation() throws ArgumentValidationException {
        final SingleOptionMissingAnnotation result =
                new CliInterfaceImpl<SingleOptionMissingAnnotation>(SingleOptionMissingAnnotation.class)
                .parseArguments(new String[] {});

        assertThat(result.getName(), equalTo(null));
    }

    @Test public void testListOptionMissingValue() throws ArgumentValidationException {
        new CliInterfaceImpl<ListOption>(ListOption.class).parseArguments(new String[] { "--name" });
    }

    @Test public void testEnumDefaultList() throws ArgumentValidationException {
        final EnumDefaultListOption result =
                new CliInterfaceImpl<EnumDefaultListOption>(EnumDefaultListOption.class)
                .parseArguments(new String[] {});

        final List<TestEnum> enumValues = result.getName();
        assertEquals(2, enumValues.size());
        assertEquals(TestEnum.Value0, enumValues.get(0));
        assertEquals(TestEnum.Value1, enumValues.get(1));
    }
}
