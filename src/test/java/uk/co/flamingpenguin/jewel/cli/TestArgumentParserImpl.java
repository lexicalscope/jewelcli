package uk.co.flamingpenguin.jewel.cli;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import junit.framework.TestCase;

public class TestArgumentParserImpl extends TestCase
{
   public void testParseArguments() throws ArgumentValidationException
   {
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{});
        final ArgumentCollection parsed = impl.parseArguments();
        assertEquals(0, parsed.getUnparsed().size());
   }

   public void testParseArgumentsNotUparsed() throws ArgumentValidationException
   {
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"-a", "1", "2", "-b", "-c", "1", "2"});
        final ArgumentCollection parsed = impl.parseArguments();
        assertTrue(parsed.contains("a"));
        assertTrue(parsed.contains("b"));
        assertTrue(parsed.contains("c"));
   }

   public void testParseArgumentsUnparsed() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"-a", "1", "2", "-b", "-c", "1", "2", "--", "3", "4"});
      final ArgumentCollection parsed = impl.parseArguments();
      assertEquals(2, parsed.getUnparsed().size());
      assertEquals("3", parsed.getUnparsed().get(0));
      assertEquals("4", parsed.getUnparsed().get(1));
   }

   public void testParseArgumentsOnlyUnparsed() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"--", "3", "4"});
      final ArgumentCollection parsed = impl.parseArguments();
      assertEquals(2, parsed.getUnparsed().size());
   }

   public void testParseArgumentsOnlyUnparsedSeperator() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"--"});
      final ArgumentCollection parsed = impl.parseArguments();
      assertEquals(0, parsed.getUnparsed().size());
   }

   public void testParseArgumentsMisplacedValue()
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"a", "-b"});
      try
      {
         impl.parseArguments();
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
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"-abc"});
        final ArgumentCollection parsed = impl.parseArguments();
        assertEquals(0, parsed.getUnparsed().size());
        assertTrue(parsed.contains("a"));
        assertTrue(parsed.contains("b"));
        assertTrue(parsed.contains("c"));
        assertFalse(parsed.contains("abc"));
   }

   public void testParseAssignedValue() throws ArgumentValidationException
   {
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"--option=value"});
        final ArgumentCollection parsed = impl.parseArguments();
        assertEquals(0, parsed.getUnparsed().size());
        assertTrue(parsed.contains("option"));
        assertEquals("value", parsed.iterator().next().getValue().get(0));
        assertFalse(parsed.contains("option=value"));
   }
}
