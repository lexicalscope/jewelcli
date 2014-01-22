package com.lexicalscope.jewel.cli.examples;

import static com.lexicalscope.jewel.cli.CliFactory.createCli;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CommandLineInterface;
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.OptionOrder;

/*
 * Copyright 2012 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class TestHelpMessageOrder {
    interface LexicographicOrder
    {
        @Option
        int getB();

        @Option
        int getA();

        @Option(helpRequest = true)
        boolean getHelp();
    }
    
    interface LexicographicOrderWithLongName
    {
        @Option(longName = "aardvark")
        int getB();

        @Option(longName = "zebra")
        int getA();

        @Option(helpRequest = true)
        boolean getHelp();
    }

    @CommandLineInterface(order = OptionOrder.DEFINITION)
    interface DefinitionOrder
    {
        @Option
        int getB();

        @Option
        int getA();

        @Option(helpRequest = true)
        boolean getHelp();
    }

    @Test public void lexicographicOrderSupported()
    {
        assertThat(createCli(LexicographicOrder.class).getHelpMessage(), containsString(String.format("%s%n\t%s", "--a value", "--b value")));
    }

    @Test public void lexicographicOrderWithLongNameSupported()
    {
        assertThat(
    		createCli(LexicographicOrderWithLongName.class).getHelpMessage(), 
    		containsString(String.format("%s%n\t%s", "--aardvark value", "--zebra value"))
		);
    }
    
    @Test public void definitionOrderSupported()
    {
    	// in recent versions of the JDK, definition order is unreliable
        assertThat(createCli(DefinitionOrder.class).getHelpMessage(), containsString("--a value"));
        assertThat(createCli(DefinitionOrder.class).getHelpMessage(), containsString("--b value"));
    }
}
