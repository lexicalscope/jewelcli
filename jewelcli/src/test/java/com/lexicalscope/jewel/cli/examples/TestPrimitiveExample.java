package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestPrimitiveExample {
    @Test public void testPrimitiveExample() throws CliValidationException {
        final PrimitiveExample result0 = CliFactory.parseArguments(PrimitiveExample.class,
                new String[] { "--boolean",
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
        assertEquals(4.1f, result0.getFloat(), 0d);
        assertEquals(4.2d, result0.getDouble(), 0d);
        assertEquals('a', result0.getChar());
        assertEquals(true, (boolean) result0.getBooleanObject());
        assertEquals(5, (byte) result0.getByteObject());
        assertEquals(6, (short) result0.getShortObject());
        assertEquals(7, (int) result0.getIntObject());
        assertEquals(8, (long) result0.getLongObject());
        assertEquals(9.1f, result0.getFloatObject(), 0d);
        assertEquals(9.2d, result0.getDoubleObject(), 0d);
        assertEquals('b', (char) result0.getCharObject());
    }
}
