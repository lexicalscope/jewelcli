package uk.co.flamingpenguin.jewel.cli;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import junit.framework.TestCase;

public class TestArgumentParserImpl extends TestCase
{
   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
   public void testParseArguments() throws ArgumentValidationException
   {
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{});
        final ParsedArguments parsed = impl.parseArguments();
        assertEquals(0, parsed.getUnparsed().size());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
   public void testParseArgumentsNotUparsed() throws ArgumentValidationException
   {
        final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"-a", "1", "2", "-b", "-c", "1", "2"});
        final ParsedArguments parsed = impl.parseArguments();
        assertTrue(parsed.contains("a"));
        assertTrue(parsed.contains("b"));
        assertTrue(parsed.contains("c"));
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
   public void testParseArgumentsUnparsed() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"-a", "1", "2", "-b", "-c", "1", "2", "--", "3", "4"});
      final ParsedArguments parsed = impl.parseArguments();
      assertEquals(2, parsed.getUnparsed().size());
      assertEquals("3", parsed.getUnparsed().get(0));
      assertEquals("4", parsed.getUnparsed().get(1));
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
   public void testParseArgumentsOnlyUnparsed() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"--", "3", "4"});
      final ParsedArguments parsed = impl.parseArguments();
      assertEquals(2, parsed.getUnparsed().size());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
   public void testParseArgumentsOnlyUnparsedSeperator() throws ArgumentValidationException
   {
      final ArgumentParserImpl impl = new ArgumentParserImpl(new String[]{"--"});
      final ParsedArguments parsed = impl.parseArguments();
      assertEquals(0, parsed.getUnparsed().size());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.impl.ArgumentParserImpl.parseArguments()'
    */
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
}
