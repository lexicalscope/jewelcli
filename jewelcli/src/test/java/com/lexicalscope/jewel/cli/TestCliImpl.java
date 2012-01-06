package com.lexicalscope.jewel.cli;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

    @Test public void testSingleOption() throws CliValidationException {
        final SingleOption option =
                new CliInterfaceImpl<SingleOption>(SingleOption.class)
                        .parseArguments(new String[] { "--name", "value" });
        assertEquals(option.getName(), "value");
    }

    @Test public void testSingleBooleanOption() throws CliValidationException {
        final SingleBooleanOption option =
                new CliInterfaceImpl<SingleBooleanOption>(SingleBooleanOption.class)
                        .parseArguments(new String[] { "--name1" });
        assertEquals(option.getName0(), false);
        assertEquals(option.isName1(), true);
    }

    @Test public void testIntegerOption() throws CliValidationException {
        final IntegerOption option =
                new CliInterfaceImpl<IntegerOption>(IntegerOption.class)
                        .parseArguments(new String[] { "--name", "10" });
        assertEquals(Integer.valueOf(10), option.getName());
    }

    @Test public void testInvalidIntegerOption() {
        try {
            new CliInterfaceImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[] { "--name", "abc" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals(1, e.getValidationErrors().size());
            assertEquals(InvalidValueForTypeException.class, e.getValidationErrors().get(0).getClass());
            assertEquals("Invalid value (Unsupported number format: For input string: \"abc\"): --name value", e
                    .getValidationErrors()
                    .get(0)
                    .getMessage());
        }
    }

    @Test public void testInvalidIntOption() {
        try {
            new CliInterfaceImpl<IntOption>(IntOption.class).parseArguments(new String[] { "--name", "abc" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals(1, e.getValidationErrors().size());
            assertEquals(InvalidValueForTypeException.class, e.getValidationErrors().get(0).getClass());
            assertEquals("Invalid value (Unsupported number format: For input string: \"abc\"): --name value", e
                    .getValidationErrors()
                    .get(0)
                    .getMessage());
        }
    }

    @Test public void testInvalidOption() {
        try {
            new CliInterfaceImpl<SingleOption>(SingleOption.class)
                    .parseArguments(new String[] { "--invalid", "value" });
            fail();
        } catch (final CliValidationException e) {
            final List<OptionValidationException> validationErrors = e.getValidationErrors();
            assertEquals(2, validationErrors.size());
            final OptionValidationException error0 = validationErrors.get(0);
            assertEquals(UnexpectedOptionException.class, error0.getClass());
            final OptionValidationException error1 = validationErrors.get(1);
            assertEquals(MissingOptionException.class, error1.getClass());
        }
    }

    @Test public void testSingleOptionalOption() throws CliValidationException {
        SingleOptionalOption option =
                new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {
                        "--name",
                        "value" });
        assertEquals(option.getName(), "value");
        assertTrue(option.isName());

        option = new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {});
        assertFalse(option.isName());
    }

    @Test public void testSingleOptionalOptionRequestMissing() throws CliValidationException {
        final SingleOptionalOption option =
                new CliInterfaceImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[] {});

        assertThat(option.getName(), equalTo(null));
    }

    @Test public void testCharacterValue() throws CliValidationException {
        final CharacterValue option =
                new CliInterfaceImpl<CharacterValue>(CharacterValue.class)
                        .parseArguments(new String[] { "--name", "a" });
        assertEquals((Character) 'a', option.getName());
    }

    @Test public void testInvalidCharacterValue() {
        try {
            new CliInterfaceImpl<CharacterValue>(CharacterValue.class).parseArguments(new String[] { "--name", "aa" });
            fail();
        } catch (final CliValidationException e) {
            final List<OptionValidationException> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(InvalidValueForTypeException.class, validationErrors.get(0).getClass());
        }
    }

    @Test public void testMethodWithArguments() throws CliValidationException {
        try {
            new CliInterfaceImpl<SingleOptionWithArgument>(SingleOptionWithArgument.class).parseArguments(new String[] {
                    "--name",
                    "value" });
            fail();
        } catch (final CliValidationException e) {
            assertEquals(
                    UnexpectedOptionException.class,
                    e.getValidationErrors().get(0).getClass());
        }
    }

    @Test public void testMethodWithMissingAnnotation() throws CliValidationException {
        final SingleOptionMissingAnnotation result =
                new CliInterfaceImpl<SingleOptionMissingAnnotation>(SingleOptionMissingAnnotation.class)
                        .parseArguments(new String[] {});

        assertThat(result.getName(), equalTo(null));
    }

    @Test public void testListOptionMissingValue() throws CliValidationException {
        new CliInterfaceImpl<ListOption>(ListOption.class).parseArguments(new String[] { "--name" });
    }

    @Test public void testEnumDefaultList() throws CliValidationException {
        final EnumDefaultListOption result =
                new CliInterfaceImpl<EnumDefaultListOption>(EnumDefaultListOption.class)
                        .parseArguments(new String[] {});

        final List<TestEnum> enumValues = result.getName();
        assertEquals(2, enumValues.size());
        assertEquals(TestEnum.Value0, enumValues.get(0));
        assertEquals(TestEnum.Value1, enumValues.get(1));
    }
}
