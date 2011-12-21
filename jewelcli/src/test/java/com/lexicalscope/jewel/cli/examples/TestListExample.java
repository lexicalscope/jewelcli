package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestListExample {
    @Test public void testListExample() throws ArgumentValidationException {
        final ListExample result0 =
                CliFactory.parseArguments(ListExample.class, new String[] { "--count", "3", "2", "1" });
        assertEquals(3, result0.getCount().size());
        assertEquals((Integer) 3, result0.getCount().get(0));
        assertEquals((Integer) 2, result0.getCount().get(1));
        assertEquals((Integer) 1, result0.getCount().get(2));

        final ListExample result1 = CliFactory.parseArguments(ListExample.class, new String[] { "--count" });
        assertEquals(0, result1.getCount().size());
    }
}
