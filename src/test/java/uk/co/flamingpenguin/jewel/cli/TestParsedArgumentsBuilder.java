package uk.co.flamingpenguin.jewel.cli;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import uk.co.flamingpenguin.jewel.cli.ParsedArgumentsBuilder;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

import junit.framework.TestCase;

public class TestParsedArgumentsBuilder extends TestCase
{
   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.ParsedArgumentsImpl.add(String)'
    */
   public void testAdd() throws ArgumentValidationException
   {
      try
      {
         final ParsedArgumentsBuilder parsedArgumentsBuilder = new ParsedArgumentsBuilder();
         parsedArgumentsBuilder.add("a");
         parsedArgumentsBuilder.add("-b");
         fail("rouge option should have been detected");
      }
      catch(final ArgumentValidationException e)
      {
         assertEquals(1, e.getValidationErrors().size());
         assertEquals(ErrorType.MisplacedOption, e.getValidationErrors().get(0).getErrorType());
      }

      new ParsedArgumentsBuilder().add("-a");

      final ParsedArgumentsBuilder parsedArgumentsBuilder = new ParsedArgumentsBuilder();
      parsedArgumentsBuilder.add("-a");
      parsedArgumentsBuilder.add("v1");
      parsedArgumentsBuilder.add("v2");
      parsedArgumentsBuilder.add("-b");
      parsedArgumentsBuilder.add("-c");
      parsedArgumentsBuilder.add("v1");
      parsedArgumentsBuilder.add("v2");
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.ParsedArgumentsImpl.setUnparsed(String[])'
    */
   public void testSetUnparsed()
   {
      final ParsedArgumentsBuilder parsedArgumentsBuilder = new ParsedArgumentsBuilder();

      final String[] unparsed = new String[]{};
      parsedArgumentsBuilder.setUnparsed(unparsed);

      final List<String> unparsedResult = parsedArgumentsBuilder.getParsedArguments().getUnparsed();
      for (int i = 0; i < unparsed.length; i++)
      {
         assertEquals(unparsed[i], unparsedResult.get(i));
      }
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.ParsedArgumentsImpl.getUnparsed()'
    */
   public void testGetUnparsed()
   {
      final ParsedArgumentsBuilder parsedArguments = new ParsedArgumentsBuilder();
      assertEquals(0, parsedArguments.getParsedArguments().getUnparsed().size());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.ParsedArgumentsImpl.getUnparsed()'
    */
   public void testNoOptions() throws ArgumentValidationException
   {
      final ParsedArgumentsBuilder parsedArgumentsBuilder = new ParsedArgumentsBuilder();
      parsedArgumentsBuilder.add("v0");
      parsedArgumentsBuilder.add("v1");
      parsedArgumentsBuilder.add("v2");
      assertEquals(3, parsedArgumentsBuilder.getParsedArguments().getUnparsed().size());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.ParsedArgumentsImpl.iterator()'
    */
   public void testIterator() throws ArgumentValidationException
   {
      final ParsedArgumentsBuilder parsedArgumentsBuilder = new ParsedArgumentsBuilder();
      parsedArgumentsBuilder.add("-a");
      parsedArgumentsBuilder.add("v1");
      parsedArgumentsBuilder.add("v2");

      final Iterator<Entry<String, List<String>>> iterator = parsedArgumentsBuilder.getParsedArguments().iterator();
      assertTrue(iterator.hasNext());

      final Entry<String, List<String>> entry = iterator.next();
      assertEquals("a", entry.getKey());
      assertEquals(2, entry.getValue().size());
      assertEquals("v1", entry.getValue().get(0));
      assertEquals("v2", entry.getValue().get(1));
   }
}
