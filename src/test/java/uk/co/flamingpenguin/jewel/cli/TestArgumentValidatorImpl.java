package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import uk.co.flamingpenguin.jewel.cli.examples.RmExample;

public class TestArgumentValidatorImpl extends TestCase
{
   public interface NoValue
   {
      @Option
      boolean getName0();

      @Option
      boolean getName1();
   }

   public interface SingleValue
   {
      @Option
      String getName();
   }

   public interface MultipleValue
   {
      @Option
      List<String> getName();
   }

   public interface ExtraValue
   {
      @Option
      List<String> getName0();

      @Option
      String getName1();
   }

   public interface OptionalOption
   {
      @Option
      String getName0();

      @Option
      String getName1();
      boolean isName1();
   }

   public interface OptionAndUnparsed
   {
      @Option
      String getName0();

      @Unparsed
      List<String> getRemainingArguments();
   }

   public void testMissingOption()
   {
      try
      {
         validate(new String[]{"--name1", "value"}, OptionalOption.class);
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.MissingOption, validationErrors.get(0).getErrorType());
      }
   }

   public void testMultipleValue() throws ArgumentValidationException
   {
      validate(new String[]{"--name", "a", "b"}, MultipleValue.class);
   }

   public void testMultipleValueEndOfArguments() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl validated = validate(new String[]{"--name", "a", "b", "--", "c", "d"}, MultipleValue.class);
      assertEquals(2, validated.getUnparsed().size());
      assertEquals(2, validated.getValues("name").size());
   }

   public void testMultipleValueNotEndOfArguments() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl validated = validate(new String[]{"--name0", "a", "b", "--name1", "c", "d", "e", "--", "f", "g"}, ExtraValue.class);
      assertEquals(4, validated.getUnparsed().size());
      assertEquals(2, validated.getValues("name0").size());
      assertEquals(1, validated.getValues("name1").size());
   }

   public void testAdjacentShortOptions() throws ArgumentValidationException
   {
      final ArgumentCollection validated = validate(new String[]{"-vrf", "./"}, RmExample.class);
      assertEquals(1, validated.getUnparsed().size());
   }

   public void testSingleValue() throws ArgumentValidationException
   {
      validate(new String[]{"--name", "a"}, MultipleValue.class);
   }

   public void testExtraOption()
   {
      try
      {
         validate(new String[]{"--name1", "value", "wrong", "--name0"}, ExtraValue.class);
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.AdditionalValue, validationErrors.get(0).getErrorType());
      }
   }

   public void testMissingValue()
   {
      try
      {
         validate(new String[]{"--name"}, SingleValue.class);
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.MissingValue, validationErrors.get(0).getErrorType());
      }
   }

   public void testUnexpectedValue()
   {
      try
      {
         validate(new String[]{"--name1", "value", "--name0"}, NoValue.class);
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.UnexpectedValue, validationErrors.get(0).getErrorType());
      }
   }

   public void testMissingMultipleValue() throws ArgumentValidationException
   {
      validate(new String[]{"--name"}, MultipleValue.class);
      // TODO[tim]:support minimum/maximum value list lengths
   }

   public void testOptionAndUnparsed() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl validated = validate(new String[] {"--name0", "value0", "remaining0"}, OptionAndUnparsed.class);
      assertEquals(1, validated.getUnparsed().size());
   }

   private <O> ArgumentCollectionImpl validate(final String[] arguments, final Class<O> klass) throws ArgumentValidationException
   {
      final ArgumentValidatorImpl<O> impl = new ArgumentValidatorImpl<O>(OptionsSpecificationImpl.<O>createOptionsSpecificationImpl(klass));
      return (ArgumentCollectionImpl) impl.validateArguments(new ParsedArgumentsBuilder().parseArguments(arguments));
   }
}
