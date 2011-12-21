/*
 * Created on Nov 15, 2011
 * Copyright 2010 by Eduard Weissmann (edi.weissmann@gmail.com).
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
package com.lexicalscope.jewel.cli.examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

/**
 * @author Eduard Weissmann
 */
public class TestOptionalMultivaluedOptions {
    interface ExampleOptions {
        @Option String getName();
        boolean isName();

        @Option List<String> getOptionalMultivaluedOption();
        boolean isOptionalMultivaluedOption();

        @Option(defaultValue = {}) List<String> getEmptyDefaultMultivaluedOption();
        @Option(defaultToNull = true) List<String> getNullDefaultMultivaluedOption();
    }

    @Test public void multivaluedOptionalOptionShouldReturnFalseIfOptionIsNotSpecified()
            throws ArgumentValidationException {
        final ExampleOptions result = CliFactory.createCli(ExampleOptions.class).parseArguments("--name", "foo");
        assertTrue(result.isName());
        assertFalse(result.isOptionalMultivaluedOption());
    }

    @Test public void multivaluedOptionalOptionShouldReturnTrueIfOptionIsSpecified()
            throws ArgumentValidationException {
        final ExampleOptions result =
                CliFactory.createCli(ExampleOptions.class).parseArguments("--optionalMultivaluedOption", "foo");
        assertThat(result.getOptionalMultivaluedOption(), contains("foo"));
    }

    @Test public void multivaluedOptionalOptionShouldReturnFalseIfNothingIsSpecified()
            throws ArgumentValidationException {
        final ExampleOptions result = CliFactory.createCli(ExampleOptions.class).parseArguments();
        assertFalse(result.isName());
        assertFalse(result.isOptionalMultivaluedOption());
    }

    @Test public void missingMultivaluedOptionalOptionShouldReturnNullIfQueried()
            throws ArgumentValidationException {
        final ExampleOptions result = CliFactory.createCli(ExampleOptions.class).parseArguments();
        assertThat(result.getOptionalMultivaluedOption(), nullValue());
    }

    @Test public void multivaluedOptionalWithEmptyListDefaultShouldReturnEmptyListWhenQueried()
            throws ArgumentValidationException {
        final ExampleOptions result = CliFactory.createCli(ExampleOptions.class).parseArguments();
        assertThat(result.getEmptyDefaultMultivaluedOption().size(), equalTo(0));
    }

    @Test public void multivaluedOptionalWithNullDefaultShouldReturnEmptyListWhenQueried()
            throws ArgumentValidationException {
        final ExampleOptions result = CliFactory.createCli(ExampleOptions.class).parseArguments();
        assertThat(result.getNullDefaultMultivaluedOption(), nullValue());
    }
}