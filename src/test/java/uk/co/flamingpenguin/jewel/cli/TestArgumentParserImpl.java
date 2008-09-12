package uk.co.flamingpenguin.jewel.cli;

import junit.framework.TestCase;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

public class TestArgumentParserImpl extends TestCase
{
   public void testParseArguments() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{});
      assertEquals(0, parsed.getUnparsed().size());
   }

   public void testParseArgumentsNotUparsed() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"-a", "1", "2", "-b", "-c", "1", "2"});
      assertTrue(parsed.containsAny("a"));
      assertTrue(parsed.containsAny("b"));
      assertTrue(parsed.containsAny("c"));
   }

   public void testParseArgumentsUnparsed() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"-a", "1", "2", "-b", "-c", "1", "2", "--", "3", "4"});
      assertEquals(2, parsed.getUnparsed().size());
      assertEquals("3", parsed.getUnparsed().get(0));
      assertEquals("4", parsed.getUnparsed().get(1));
   }

   public void testParseArgumentsOnlyUnparsed() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"--", "3", "4"});
      assertEquals(2, parsed.getUnparsed().size());
   }

   public void testParseArgumentsOnlyUnparsedSeperator() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"--"});
      assertEquals(0, parsed.getUnparsed().size());
   }

   public void testParseArgumentsMisplacedValue()
   {
      try
      {
         parseArguments(new String[]{"a", "-b"});
         fail();
      }
      catch (final ArgumentValidationException e)
      {
          assertEquals(1, e.getValidationErrors().size());
          assertEquals(ErrorType.MisplacedOption, e.getValidationErrors().get(0).getErrorType());
      }
   }

   public void testParseShortArguments() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"-abc"});
      assertEquals(0, parsed.getUnparsed().size());
      assertTrue(parsed.containsAny("a"));
      assertTrue(parsed.containsAny("b"));
      assertTrue(parsed.containsAny("c"));
      assertFalse(parsed.containsAny("abc"));
   }

   public void testParseAssignedValue() throws ArgumentValidationException
   {
      final ArgumentCollectionImpl parsed = parseArguments(new String[]{"--option=value"});
      assertEquals(0, parsed.getUnparsed().size());
      assertTrue(parsed.containsAny("option"));
      assertEquals("value", parsed.iterator().next().getValues().get(0));
      assertFalse(parsed.containsAny("option=value"));
   }

   private ArgumentCollectionImpl parseArguments(final String[] arguments) throws ArgumentValidationException
   {
      final ArgumentParser impl = new ParsedArgumentsBuilder();
      return (ArgumentCollectionImpl) impl.parseArguments(arguments);
   }
}
