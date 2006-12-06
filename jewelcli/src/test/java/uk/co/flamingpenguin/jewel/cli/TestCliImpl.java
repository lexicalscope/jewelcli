package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import junit.framework.TestCase;

public class TestCliImpl extends TestCase
{
   public interface SingleOption
   {
      @Option
      String getName();
   }

   public interface CharacterValue
   {
      @Option
      Character getName();
   }

   public interface SingleBooleanOption
   {
      @Option
      boolean getName0();

      @Option
      boolean isName1();
   }

   public interface SingleOptionalOption
   {
      @Option
      String getName();

      boolean isName();
   }

   public interface IntegerOption
   {
      @Option
      Integer getName();
   }

   public interface IntOption
   {
      @Option
      int getName();
   }

   public interface SingleOptionWithArgument
   {
      @Option
      String getName(String argument);
   }

   public interface SingleOptionMissingAnnotation
   {
      String getName();
   }

   public void testSingleOption() throws ArgumentValidationException
   {
       final SingleOption option = new CliImpl<SingleOption>(SingleOption.class).parseArguments(new String[]{"--name", "value"});
       assertEquals(option.getName(), "value");
   }

   public void testSingleBooleanOption() throws ArgumentValidationException
   {
       final SingleBooleanOption option = new CliImpl<SingleBooleanOption>(SingleBooleanOption.class).parseArguments(new String[]{"--name1"});
       assertEquals(option.getName0(), false);
       assertEquals(option.isName1(), true);
   }

   public void testIntegerOption() throws ArgumentValidationException
   {
       final IntegerOption option = new CliImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[]{"--name", "10"});
       assertEquals(Integer.valueOf(10), option.getName());
   }

   public void testInvalidIntegerOption()
   {
      try
      {
       new CliImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[]{"--name", "abc"});
       fail();
      }
      catch(final ArgumentValidationException e)
      {
         assertEquals(1, e.getValidationErrors().size());
         assertEquals(ErrorType.InvalidValueForType, e.getValidationErrors().get(0).getErrorType());
         assertEquals("Unsupported number format: For input string: \"abc\"", e.getValidationErrors().get(0).getMessage());
      }
   }

   public void testInvalidIntOption()
   {
      try
      {
       new CliImpl<IntOption>(IntOption.class).parseArguments(new String[]{"--name", "abc"});
       fail();
      }
      catch(final ArgumentValidationException e)
      {
         assertEquals(1, e.getValidationErrors().size());
         assertEquals(ErrorType.InvalidValueForType, e.getValidationErrors().get(0).getErrorType());
         assertEquals("Unsupported number format: For input string: \"abc\"", e.getValidationErrors().get(0).getMessage());
      }
   }

   public void testInvalidOption() throws ArgumentValidationException
   {
      try
      {
         new CliImpl<SingleOption>(SingleOption.class).parseArguments(new String[]{"--invalid", "value"});
         fail();
      }
      catch(final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(2, validationErrors.size());
         final ValidationError error0 = validationErrors.get(0);
         assertEquals(ErrorType.MissingOption, error0.getErrorType());
         final ValidationError error1 = validationErrors.get(1);
         assertEquals(ErrorType.UnexpectedOption, error1.getErrorType());
      }
   }

   public void testSingleOptionalOption() throws ArgumentValidationException
   {
       SingleOptionalOption option = new CliImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[]{"--name", "value"});
       assertEquals(option.getName(), "value");
       assertTrue(option.isName());

       option = new CliImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[]{});
       assertFalse(option.isName());
   }

   public void testCharacterValue() throws ArgumentValidationException
   {
      final CharacterValue option = new CliImpl<CharacterValue>(CharacterValue.class).parseArguments(new String[]{"--name", "a"});
      assertEquals((Character) 'a', option.getName());
   }

   public void testInvalidCharacterValue()
   {
      try
      {
         new CliImpl<CharacterValue>(CharacterValue.class).parseArguments(new String[]{"--name", "aa"});
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.InvalidValueForType, validationErrors.get(0).getErrorType());
      }
   }

   public void testMethodWithArguments() throws ArgumentValidationException
   {
      try
      {
         final SingleOptionWithArgument result = new CliImpl<SingleOptionWithArgument>(SingleOptionWithArgument.class).parseArguments(new String[]{"--name", "value"});
         result.getName("fred");
         fail();
      }
      catch (final UnsupportedOperationException e)
      {
         assertEquals("Method (public abstract java.lang.String uk.co.flamingpenguin.jewel.cli.TestCliImpl$SingleOptionWithArgument.getName(java.lang.String)) with arguments not supported for reading argument values", e.getMessage());
      }
   }

   public void testMethodWithMissingAnnotation() throws ArgumentValidationException
   {
      try
      {
         final SingleOptionMissingAnnotation result = new CliImpl<SingleOptionMissingAnnotation>(SingleOptionMissingAnnotation.class).parseArguments(new String[]{});
         result.getName();
         fail();
      }
      catch (final UnsupportedOperationException e)
      {
         assertEquals("Method (public abstract java.lang.String uk.co.flamingpenguin.jewel.cli.TestCliImpl$SingleOptionMissingAnnotation.getName()) is not annotated for option specification", e.getMessage());
      }
   }
}
