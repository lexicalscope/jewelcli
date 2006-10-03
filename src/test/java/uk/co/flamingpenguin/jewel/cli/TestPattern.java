package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import junit.framework.TestCase;

public class TestPattern extends TestCase
{
   public interface TestStringPattern
   {
      @Option(pattern = "[a-z]+")
      String getOption();
   }

   public void testStringPatternMismatch()
   {
      try
      {
         CliFactory.parseArguments(TestStringPattern.class, "--option", "ABC");
         fail();
      }
      catch (final ArgumentValidationException e)
      {
         final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
         assertEquals(1, validationErrors.size());
         assertEquals(ErrorType.PatternMismatch, validationErrors.get(0).getErrorType());
         assertEquals("Cannot match (ABC) to pattern : --option /[a-z]+/", validationErrors.get(0).toString());
      }
   }

   public void testStringPattern() throws ArgumentValidationException
   {
      CliFactory.parseArguments(TestStringPattern.class, "--option", "abc");
   }
}
