package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;
import junit.framework.TestCase;

public class TestPrimitiveExample extends TestCase
{
   public void testPrimitiveExample() throws ArgumentValidationException
   {
      final PrimitiveExample result0 = CliFactory.parseArguments(PrimitiveExample.class,
            new String[]{"--boolean",
                         "--byte", "1",
                         "--short", "2",
                         "--int", "3",
                         "--long", "4",
                         "--float", "4.1",
                         "--double", "4.2",
                         "--char", "a",
                         "--booleanObject",
                         "--byteObject", "5",
                         "--shortObject", "6",
                         "--intObject", "7",
                         "--longObject", "8",
                         "--floatObject", "9.1",
                         "--doubleObject", "9.2",
                         "--charObject", "b",
                         });

      assertEquals(true, result0.getBoolean());
      assertEquals(1, result0.getByte());
      assertEquals(2, result0.getShort());
      assertEquals(3, result0.getInt());
      assertEquals(4, result0.getLong());
      assertEquals(4.1f, result0.getFloat());
      assertEquals(4.2d, result0.getDouble());
      assertEquals('a', result0.getChar());
      assertEquals(true, (boolean) result0.getBooleanObject());
      assertEquals(5, (byte) result0.getByteObject());
      assertEquals(6, (short) result0.getShortObject());
      assertEquals(7, (int) result0.getIntObject());
      assertEquals(8, (long) result0.getLongObject());
      assertEquals(9.1f, (float) result0.getFloatObject());
      assertEquals(9.2d, (double) result0.getDoubleObject());
      assertEquals('b', (char) result0.getCharObject());
   }
}
