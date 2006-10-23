package uk.co.flamingpenguin.jewel.cli;

import junit.framework.TestCase;

public class TestInheritedOptions extends TestCase
{
   public interface SuperInterface
   {
      @Option
      boolean getSuperOption();
   }

   public interface SubInterface extends SuperInterface
   {
      @Option
      boolean getSubOption();
   }

   public void testSubInterface() throws ArgumentValidationException
   {
      CliFactory.parseArguments(SubInterface.class, new String[]{"--superOption", "--superOption"});
   }
}
