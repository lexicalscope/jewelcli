package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

public class TestArgumentValidatorImpl extends TestCase
{
   public interface NoValue
   {
      @Option
      boolean getName();
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

   public void testMissingOption()
   {
      try
      {
         validate(new String[]{"-name1", "value"}, OptionalOption.class);
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
      validate(new String[]{"-name", "a", "b"}, MultipleValue.class);
   }

   public void testMultipleValueEndOfArguments() throws ArgumentValidationException
   {
      final ValidatedArguments validated = validate(new String[]{"-name", "a", "b", "--", "c", "d"}, MultipleValue.class);
      assertEquals(2, validated.getUnparsed().size());
      assertEquals(2, validated.getValues("name").size());
   }

   public void testMultipleValueNotEndOfArguments() throws ArgumentValidationException
   {
      final ValidatedArguments validated = validate(new String[]{"-name0", "a", "b", "-name1", "c", "d", "e", "--", "f", "g"}, ExtraValue.class);
      assertEquals(4, validated.getUnparsed().size());
      assertEquals(2, validated.getValues("name0").size());
      assertEquals(1, validated.getValues("name1").size());
   }

   public void testSingleValue() throws ArgumentValidationException
   {
      validate(new String[]{"-name", "a"}, MultipleValue.class);
   }

   public void testExtraOption() throws ArgumentValidationException
   {
      try
      {
         validate(new String[]{"-name1", "value", "wrong", "-name0"}, ExtraValue.class);
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
         validate(new String[]{"-name"}, SingleValue.class);
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
         validate(new String[]{"-name", "value"}, NoValue.class);
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
      validate(new String[]{"-name"}, MultipleValue.class);
      // TODO[tim]:support minimum/maximum value list lengths
   }

   private <O> ValidatedArguments validate(final String[] arguments, final Class<O> klass) throws ArgumentValidationException
   {
      final ArgumentValidatorImpl<O> impl = new ArgumentValidatorImpl<O>(new OptionsSpecificationImpl<O>(klass));
      return impl.validateArguments(new ArgumentParserImpl(arguments).parseArguments());
   }
}
