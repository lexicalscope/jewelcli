package uk.co.flamingpenguin.jewel.cli;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class TestInvalidSpecificationErrorHandling {
    @Rule public final ExpectedException exception = ExpectedException.none();

    public interface InvalidDefaultOptionSpecification {
        @Option(defaultToNull = true, defaultValue = { "another value" }) public String incorrectlySpecified();
    }

    @Test public void testNullAndNonNullDefaults()
    {
        exception.expect(OptionSpecificationException.class);
        exception
                .expectMessage(equalTo("option cannot have null default and non-null default value: public java.lang.String incorrectlySpecified()"));
        CliFactory.createCli(InvalidDefaultOptionSpecification.class);
    }
}
