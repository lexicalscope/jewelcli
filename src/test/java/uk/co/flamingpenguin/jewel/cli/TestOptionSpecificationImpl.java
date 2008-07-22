package uk.co.flamingpenguin.jewel.cli;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;
import uk.co.flamingpenguin.jewel.cli.Option;
import uk.co.flamingpenguin.jewel.cli.OptionSpecification;
import uk.co.flamingpenguin.jewel.cli.OptionSpecificationImpl;

public class TestOptionSpecificationImpl extends TestCase
{
   {
      Logger.getLogger(OptionSpecificationImpl.class.getName()).setLevel(Level.FINEST);
   }

   public interface HasShortName
   {
      @Option(shortName="n")
      String getName1();

      @Option
      String getName2();
   }

   public interface ShortName
   {
      @Option(shortName="")
      String getName0();

      @Option(shortName="n")
      String getName1();

      @Option(shortName="excessive")
      String getName2();
   }

   public interface LongName
   {
      @Option(longName="")
      String getName0();

      @Option(longName="totallyDifferent")
      String getName1();

      @Option(longName="name2")
      String getName2();
   }

   public interface Value
   {
      @Option
      String getName();

      @Option
      boolean getDebug();
   }

   public interface Name
   {
      @Option
      String getName();

      @Option
      String name();

      @Option
      boolean isDebug();

      @Option
      boolean debug();
   }

   public interface Type
   {
      @Option
      String getString();

      @Option
      Integer getInteger();

      @Option
      int getInt();

      @Option
      List<String> getStringList();

      @SuppressWarnings("unchecked")
      @Option
      List getList();
   }

   public interface MultiValued
   {
      @Option
      List<String> getStringList();

      @SuppressWarnings("unchecked")
      @Option
      List getList();
   }

   public interface HasOptionalOption
   {
      @Option
      String getName1();

      @Option
      String getName2();

      boolean isName2();
   }

   public interface ToString
   {
      @Option(longName="aLongName")
      String getLongName();

      @Option(shortName="s")
      String getShortName();

      @Option
      String getOptional();
      boolean isOptional();

      @Option
      List<String> getOptionalMulti();
      boolean isOptionalMulti();

      @Option(description="this is a description")
      List<String> getDescription();

      @Option(description="this is a description", shortName="a")
      List<String> getAll();
      boolean isAll();
   }

   public interface Summary
   {
      @Option(shortName="s")
      String getShortName();

      @Option(longName="aLongName")
      String getLongName();

      @Option(description="this is a description")
      String getWithDescription();

      @Option(longName="aLongName", shortName="s")
      String getLongNameShortName();

      @Option(pattern="[a-z]")
      char getPattern();

      @Option
      String getOptional();
      boolean isOptional();

      @Option
      List<String> getList();

      @Option
      List<String> getOptionalList();
      boolean isOptionalList();

      @Option(longName="aLongName", shortName="s")
      List<String> getOptionalListShortNameLongName();
      boolean isOptionalListShortNameLongName();
   }

   public void testSumary() throws NoSuchMethodException
   {
      checkSummary("getShortName", "--shortName -s value");
      checkSummary("getLongName", "--aLongName value");
      checkSummary("getWithDescription", "--withDescription value");
      checkSummary("getLongNameShortName", "--aLongName -s value");
      checkSummary("getOptional", "[--optional value]");
      checkSummary("getList", "--list value...");
      checkSummary("getOptionalList", "[--optionalList value...]");
      checkSummary("getOptionalListShortNameLongName", "[--aLongName -s value...]");
      checkSummary("getPattern", "--pattern /[a-z]/");

      // TODO[tim]: test summary
      // TODO[tim]: test option specifications (plural) summary.
   }

   private void checkSummary(String method, String expectedSummary) throws NoSuchMethodException
   {
      assertEquals(expectedSummary, createOption(Summary.class, method).getSummary(new StringBuilder()).toString());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.toString()'
    */
   public void testToString() throws NoSuchMethodException
   {
      assertEquals("--aLongName value", createOption(ToString.class, "getLongName").toString());
      assertEquals("--shortName -s value", createOption(ToString.class, "getShortName").toString());
      assertEquals("[--optional value]", createOption(ToString.class, "getOptional").toString());
      assertEquals("[--optionalMulti value...]", createOption(ToString.class, "getOptionalMulti").toString());
      assertEquals("--description value... : this is a description", createOption(ToString.class, "getDescription").toString());
      assertEquals("[--all -a value...] : this is a description", createOption(ToString.class, "getAll").toString());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.isMultiValued()'
    */
   public void testIsMultiValued() throws NoSuchMethodException
   {
      assertTrue(createOption(MultiValued.class, "getStringList").isMultiValued());
      assertTrue(createOption(MultiValued.class, "getList").isMultiValued());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.getType()'
    */
   public void testGetType() throws NoSuchMethodException
   {
      assertEquals(String.class, createOption(Type.class, "getString").getType());
      assertEquals(Integer.class, createOption(Type.class, "getInteger").getType());
      assertEquals(int.class, createOption(Type.class, "getInt").getType());
      assertEquals(String.class, createOption(Type.class, "getStringList").getType());
      assertEquals(String.class, createOption(Type.class, "getList").getType());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.getName()'
    */
   public void testGetName() throws SecurityException, NoSuchMethodException
   {
      assertEquals("name", createOption(Name.class, "getName").getName());
      assertEquals("name", createOption(Name.class, "name").getName());
      assertEquals("debug", createOption(Name.class, "isDebug").getName());
      assertEquals("debug", createOption(Name.class, "debug").getName());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.getShortName()'
    */
   public void testGetShortName() throws SecurityException, NoSuchMethodException
   {
      assertEquals(0, createOption(ShortName.class, "getName0").getShortNames().size());
      assertEquals("n", createOption(ShortName.class, "getName1").getShortNames().get(0));
      assertEquals("e", createOption(ShortName.class, "getName2").getShortNames().get(0));
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.getLongName()'
    */
   public void testGetLongName() throws SecurityException, NoSuchMethodException
   {
      assertEquals("name0", createOption(LongName.class, "getName0").getLongName());
      assertEquals("totallyDifferent", createOption(LongName.class, "getName1").getLongName());
      assertEquals("name2", createOption(LongName.class, "getName2").getLongName());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.hasValue()'
    */
   public void testHasValue() throws SecurityException, NoSuchMethodException
   {
      assertTrue(createOption(Value.class, "getName").hasValue());
      assertFalse(createOption(Value.class, "getDebug").hasValue());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.hasShortName()'
    */
   public void testHasShortName() throws SecurityException, NoSuchMethodException
   {
      assertTrue(createOption(HasShortName.class, "getName1").hasShortName());
      assertFalse(createOption(HasShortName.class, "getName2").hasShortName());
   }

   /*
    * Test method for 'uk.co.flamingpenguin.jewel.cli.OptionSpecification.isOptional()'
    */
   public void testIsOptional() throws SecurityException, NoSuchMethodException
   {
      assertFalse(createOption(HasOptionalOption.class, "getName1").isOptional());
      assertTrue(createOption(HasOptionalOption.class, "getName2").isOptional());
   }

   private OptionSpecification createOption(Class<?> klass, final String methodName) throws NoSuchMethodException
   {
      return new OptionSpecificationImpl(klass.getMethod(methodName, (Class[]) null), klass);
   }
}
