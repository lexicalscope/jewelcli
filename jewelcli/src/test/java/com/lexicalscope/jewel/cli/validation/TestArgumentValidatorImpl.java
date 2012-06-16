package com.lexicalscope.jewel.cli.validation;

import static com.lexicalscope.jewel.cli.ValidationFailureMatcher.validationError;
import static com.lexicalscope.jewel.cli.parser.DefaultArgumentParserFactory.createDefaultArgumentParser;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentCollectionBuilder;
import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;
import com.lexicalscope.jewel.cli.ValidationErrorBuilder;
import com.lexicalscope.jewel.cli.ValidationFailureType;
import com.lexicalscope.jewel.cli.examples.RmExample;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

public class TestArgumentValidatorImpl<O> {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock public OptionsSpecification<O> specification;
    @Mock public ValidationErrorBuilder validationErrorBuilder;

    @Mock public ParsedOptionSpecification option;

    private ArgumentValidatorImpl<O> argumentValidator;

    @Before
    public void setup() {
        argumentValidator = new ArgumentValidatorImpl<O>(specification, validationErrorBuilder);
    }

    public interface NoValue {
        @Option boolean getName0();

        @Option boolean getName1();
    }

    public interface SingleValue {
        @Option String getName();
    }

    public interface MultipleValue {
        @Option List<String> getName();

        @Unparsed List<String> getUnparsed();
    }

    public interface ExtraValue {
        @Option List<String> getName0();

        @Option String getName1();

        @Unparsed List<String> getUnparsed();
    }

    public interface OptionalOption {
        @Option String getName0();

        @Option String getName1();
        boolean isName1();
    }

    public interface OptionAndUnparsed {
        @Option String getName0();

        @Unparsed List<String> getRemainingArguments();
    }

    @Test public void testMissingOption() {
        context.checking(new Expectations() {{
            oneOf(validationErrorBuilder).validate();
            oneOf(specification).getMandatoryOptions(); will(returnValue(asList(option)));
            oneOf(validationErrorBuilder).missingOption(option);
            oneOf(validationErrorBuilder).validate();
        }});
        argumentValidator.finishedProcessing();
    }

    @Test public void noUnparsedSpecificationAndNoUnparsedOptionsIsValid() {
        context.checking(new Expectations() {{
            oneOf(specification).hasUnparsedSpecification(); will(returnValue(false));
        }});
        argumentValidator.processUnparsed(Collections.<String>emptyList());
    }

    @Test public void testMultipleValue() throws ArgumentValidationException {
        context.checking(new Expectations() {{
            oneOf(specification).isSpecified("name"); will(returnValue(true));
            oneOf(specification).getSpecification("name"); will(returnValue(option));
            oneOf(option).isHelpOption(); will(returnValue(false));
            oneOf(option).allowedThisManyValues(2); will(returnValue(true));

            oneOf(option).allowedValue("a"); will(returnValue(true));
            oneOf(option).allowedValue("b"); will(returnValue(true));

            oneOf(validationErrorBuilder).validate();
            oneOf(specification).getMandatoryOptions(); will(returnValue(asList(option)));
            oneOf(validationErrorBuilder).validate();
        }});

        argumentValidator.processOption("name", asList("a", "b"));
        argumentValidator.finishedProcessing();
    }

    @Test @Ignore public void testMultipleValueEndOfArguments() throws ArgumentValidationException {
        final OptionCollectionImpl validated =
                validate(new String[] { "--name", "a", "b", "--", "c", "d" }, MultipleValue.class);
        assertEquals(2, validated.getUnparsed().size());
        assertEquals(2, validated.getValues("name").size());
    }

    @Test @Ignore public void testMultipleValueNotEndOfArguments() throws ArgumentValidationException {
        final OptionCollectionImpl validated =
                validate(
                        new String[] { "--name0", "a", "b", "--name1", "c", "d", "e", "--", "f", "g" },
                        ExtraValue.class);
        assertEquals(4, validated.getUnparsed().size());
        assertEquals(2, validated.getValues("name0").size());
        assertEquals(1, validated.getValues("name1").size());
    }

    @Test @Ignore public void testAdjacentShortOptions() throws ArgumentValidationException {
        final OptionCollectionImpl validated = validate(new String[] { "-vrf", "./" }, RmExample.class);
        assertEquals(1, validated.getUnparsed().size());
    }

    @Test @Ignore public void testSingleValue() throws ArgumentValidationException {
        validate(new String[] { "--name", "a" }, MultipleValue.class);
    }

    @Test @Ignore public void testExtraOption() {
        try {
            validate(new String[] { "--name1", "value", "wrong", "--name0" }, ExtraValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.UnexpectedAdditionalValue)));
        }
    }

    @Test @Ignore public void testMissingValue() {
        try {
            validate(new String[] { "--name" }, SingleValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.MissingValue)));
        }
    }

    @Test @Ignore public void testUnexpectedValue() {
        try {
            validate(new String[] { "--name1", "value", "--name0" }, NoValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            assertThat(e.getValidationFailures(), contains(validationError(ValidationFailureType.UnexpectedValue)));
        }
    }

    @Test @Ignore public void testMissingMultipleValue() throws ArgumentValidationException {
        validate(new String[] { "--name" }, MultipleValue.class);
        // TODO[tim]:support minimum/maximum value list lengths
    }

    @Test @Ignore public void testOptionAndUnparsed() throws ArgumentValidationException {
        final OptionCollectionImpl validated =
                validate(new String[] { "--name0", "value0", "remaining0" }, OptionAndUnparsed.class);
        assertEquals(1, validated.getUnparsed().size());
    }

    private <O> OptionCollectionImpl validate(final String[] arguments, final Class<O> klass)
            throws ArgumentValidationException {
        final ArgumentValidatorImpl<O> impl = null;
//                new ArgumentValidatorImpl<O>(
//                        InterfaceOptionsSpecificationParser.<O>createOptionsSpecificationImpl(type(klass)));

        final ArgumentCollectionBuilder parsedArguments = new ArgumentCollectionBuilder();
        createDefaultArgumentParser().parseArguments(parsedArguments, arguments);

        return (OptionCollectionImpl) parsedArguments.processArguments(impl);
    }
}
