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

   public void testSingleOption() throws ArgumentValidationException, InvalidArgumentsException
   {
       final SingleOption option = new CliImpl<SingleOption>(SingleOption.class).parseArguments(new String[]{"-name", "value"});
       assertEquals(option.getName(), "value");
   }

   public void testIntegerOption() throws ArgumentValidationException, InvalidArgumentsException
   {
       final IntegerOption option = new CliImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[]{"-name", "10"});
       assertEquals(Integer.valueOf(10), option.getName());
   }

   public void testInvalidIntegerOption() throws InvalidArgumentsException
   {
      try
      {
       final IntegerOption option = new CliImpl<IntegerOption>(IntegerOption.class).parseArguments(new String[]{"-name", "abc"});
       fail();
      }
      catch(ArgumentValidationException e)
      {
         assertEquals(1, e.getValidationErrors().size());
         assertEquals(ErrorType.InvalidValueForType, e.getValidationErrors().get(0).getErrorType());
      }
   }

   public void testInvalidOption() throws ArgumentValidationException, InvalidArgumentsException
   {
      try
      {
         new CliImpl<SingleOption>(SingleOption.class).parseArguments(new String[]{"-invalid", "value"});
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

   public void testSingleOptionalOption() throws ArgumentValidationException, InvalidArgumentsException
   {
       SingleOptionalOption option = new CliImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[]{"-name", "value"});
       assertEquals(option.getName(), "value");
       assertTrue(option.isName());

       option = new CliImpl<SingleOptionalOption>(SingleOptionalOption.class).parseArguments(new String[]{});
       assertFalse(option.isName());
   }
}
