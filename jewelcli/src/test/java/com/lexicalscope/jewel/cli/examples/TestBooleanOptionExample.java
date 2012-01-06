package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

/*
 * Copyright 2011 Tim Wood
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

public class TestBooleanOptionExample {
    interface BooleanOptionExample
    {
        @Option boolean getOptionOne();

        @Option Boolean getOptionTwo();
    }

    @Test public void specifiedBooleanOptionsReturnTrue() {
        final BooleanOptionExample cli =
                CliFactory.parseArguments(BooleanOptionExample.class, "--optionOne", "--optionTwo");

        assertTrue("option one is present", cli.getOptionOne());
        assertTrue("option two is present", cli.getOptionTwo());
    }

    @Test public void omittedBooleanOptionsReturnFalse() {
        final BooleanOptionExample cli =
                CliFactory.parseArguments(BooleanOptionExample.class);

        assertTrue("option one is not present", !cli.getOptionOne());
        assertTrue("option two is not present", !cli.getOptionTwo());
    }
}
