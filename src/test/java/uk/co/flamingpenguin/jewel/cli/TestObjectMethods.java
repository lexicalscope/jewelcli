/*
 * Copyright 2007 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.flamingpenguin.jewel.cli;

import junit.framework.TestCase;


public class TestObjectMethods extends TestCase
{
   public interface SingleOption
   {
      @Option
      String getName();
   }

    public void testHashCode() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", " value");
       parsedArguments.hashCode();
    }

    public void testEquals() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", " value");
       assertTrue(parsedArguments.equals(parsedArguments));
    }

    public void testNotEquals() throws ArgumentValidationException
    {
       final SingleOption parsedArguments0 = CliFactory.parseArguments(SingleOption.class, "--name", " value");
       final SingleOption parsedArguments1 = CliFactory.parseArguments(SingleOption.class, "--name", " value");
       assertFalse(parsedArguments0.equals(parsedArguments1));
    }

    public void testToString() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", " value");
       assertEquals("uk.co.flamingpenguin.jewel.cli.TestObjectMethods$SingleOption", parsedArguments.toString());
    }
}
