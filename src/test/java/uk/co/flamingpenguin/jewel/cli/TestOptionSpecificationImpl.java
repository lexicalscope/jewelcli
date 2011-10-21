package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestOptionSpecificationImpl {
    @Rule public ExpectedException exception = ExpectedException.none();

    public interface HasShortName {
        @Option(shortName = "n") String getName1();

        @Option String getName2();
    }

    public interface ShortName {
        @Option(shortName = "") String getName0();

        @Option(shortName = "n") String getName1();

        @Option(shortName = "excessive") String getName2();
    }

    public interface LongName {
        @Option() String getName0();

        @Option(longName = "totallyDifferent") String getName1();

        @Option(longName = "name2") String getName2();
    }

    public interface InvalidLongName {
        @Option(longName = "") String getName0();
    }

    public interface Value {
        @Option String getName();

        @Option boolean getDebug();
    }

    public interface Name {
        @Option String getName();

        @Option String name();

        @Option boolean isDebug();

        @Option boolean debug();
    }

    public interface Type {
        @Option String getString();

        @Option Integer getInteger();

        @Option int getInt();

        @Option List<String> getStringList();

        @SuppressWarnings("rawtypes") @Option List getList();
    }

    public interface MultiValued {
        @Option List<String> getStringList();

        @SuppressWarnings("rawtypes") @Option List getList();
    }

    public interface HasOptionalOption {
        @Option String getName1();

        @Option String getName2();

        boolean isName2();
    }

    public interface ToString {
        @Option(longName = "aLongName") String getLongName();

        @Option(shortName = "s") String getShortName();

        @Option String getOptional();
        boolean isOptional();

        @Option List<String> getOptionalMulti();
        boolean isOptionalMulti();

        @Option(description = "this is a description") List<String> getDescription();

        @Option(description = "this is a description", shortName = "a") List<String> getAll();
        boolean isAll();
    }

    public interface Summary {
        @Option(shortName = "s") String getShortName();

        @Option(longName = "aLongName") String getLongName();

        @Option(description = "this is a description") String getWithDescription();

        @Option(longName = "aLongName", shortName = "s") String getLongNameShortName();

        @Option(pattern = "[a-z]") char getPattern();

        @Option String getOptional();
        boolean isOptional();

        @Option List<String> getList();

        @Option List<String> getOptionalList();
        boolean isOptionalList();

        @Option(longName = "aLongName", shortName = "s") List<String> getOptionalListShortNameLongName();
        boolean isOptionalListShortNameLongName();
    }

    @Test public void testSumary() throws NoSuchMethodException {
        checkSummary("getShortName", "--shortName -s value");
        checkSummary("getLongName", "--aLongName value");
        checkSummary("getWithDescription", "--withDescription value : this is a description");
        checkSummary("getLongNameShortName", "--aLongName -s value");
        checkSummary("getOptional", "[--optional value]");
        checkSummary("getList", "--list value...");
        checkSummary("getOptionalList", "[--optionalList value...]");
        checkSummary("getOptionalListShortNameLongName", "[--aLongName -s value...]");
        checkSummary("getPattern", "--pattern /[a-z]/");

        // TODO[tim]: test summary
        // TODO[tim]: test option specifications (plural) summary.
    }

    private void checkSummary(final String method, final String expectedSummary) throws NoSuchMethodException {
        final OptionSpecification option = createOption(Summary.class, method);
        assertEquals(expectedSummary, new OptionSummary(option).toString());
    }

    @Test public void testToString() throws NoSuchMethodException {
        assertEquals("--aLongName value", createOption(ToString.class, "getLongName").toString());
        assertEquals("--shortName -s value", createOption(ToString.class, "getShortName").toString());
        assertEquals("[--optional value]", createOption(ToString.class, "getOptional").toString());
        assertEquals("[--optionalMulti value...]", createOption(ToString.class, "getOptionalMulti").toString());
        assertEquals("--description value... : this is a description", createOption(ToString.class, "getDescription")
                .toString());
        assertEquals("[--all -a value...] : this is a description", createOption(ToString.class, "getAll").toString());
    }

    @Test public void testIsMultiValued() throws NoSuchMethodException {
        assertTrue(createOption(MultiValued.class, "getStringList").isMultiValued());
        assertTrue(createOption(MultiValued.class, "getList").isMultiValued());
    }

    @Test public void testGetType() throws NoSuchMethodException {
        assertEquals(String.class, createOption(Type.class, "getString").getType());
        assertEquals(Integer.class, createOption(Type.class, "getInteger").getType());
        assertEquals(int.class, createOption(Type.class, "getInt").getType());
        assertEquals(String.class, createOption(Type.class, "getStringList").getType());
        assertEquals(String.class, createOption(Type.class, "getList").getType());
    }

    @Test public void testGetName() throws SecurityException, NoSuchMethodException {
        assertThat(createOption(Name.class, "getName").getLongName(), contains("name"));
        assertThat(createOption(Name.class, "name").getLongName(), contains("name"));
        assertThat(createOption(Name.class, "isDebug").getLongName(), contains("debug"));
        assertThat(createOption(Name.class, "debug").getLongName(), contains("debug"));
    }

    @Test public void testGetShortName() throws SecurityException, NoSuchMethodException {
        assertEquals(0, createOption(ShortName.class, "getName0").getShortNames().size());
        assertEquals("n", createOption(ShortName.class, "getName1").getShortNames().get(0));
        assertEquals("e", createOption(ShortName.class, "getName2").getShortNames().get(0));
    }

    @Test public void testGetLongName() throws SecurityException, NoSuchMethodException {
        assertThat(createOption(LongName.class, "getName0").getLongName(), contains("name0"));
        assertThat(createOption(LongName.class, "getName1").getLongName(), contains("totallyDifferent"));
        assertThat(createOption(LongName.class, "getName2").getLongName(), contains("name2"));
    }

    @Test public void testEmptyLongNameIsNotAllowed() throws SecurityException, NoSuchMethodException {
        exception.expect(OptionSpecificationException.class);
        exception.expectMessage("option public java.lang.String getName0() long name cannot be blank");

        createOption(InvalidLongName.class, "getName0");
    }

    @Test public void testHasValue() throws SecurityException, NoSuchMethodException {
        assertTrue(createOption(Value.class, "getName").hasValue());
        assertFalse(createOption(Value.class, "getDebug").hasValue());
    }

    @Test public void testHasShortName() throws SecurityException, NoSuchMethodException {
        assertTrue(createOption(HasShortName.class, "getName1").hasShortName());
        assertFalse(createOption(HasShortName.class, "getName2").hasShortName());
    }

    @Test public void testIsOptional() throws SecurityException, NoSuchMethodException {
        assertFalse(createOption(HasOptionalOption.class, "getName1").isOptional());
        assertTrue(createOption(HasOptionalOption.class, "getName2").isOptional());
    }

    private OptionSpecification createOption(final Class<?> klass, final String methodName)
            throws NoSuchMethodException {
        final Method method = klass.getMethod(methodName, (Class[]) null);

        return new ConvertOptionMethodToOptionSpecification(type(klass)).convert(method(method));
    }
}
