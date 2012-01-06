package com.lexicalscope.jewel.cli.examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

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

public class TestEnumOptionExample {
    public enum ExampleEnum {
        Value1, Value2, Value3
    }

    interface EnumOptionExample
    {
        @Option ExampleEnum getOptionOne();
        boolean isOptionOne();

        @Option List<ExampleEnum> getOptionTwo();
        boolean isOptionTwo();
    }

    @Test public void specifiedSingleValueEnumOptionGivesEnumValue() {
        final EnumOptionExample cli =
                CliFactory.parseArguments(EnumOptionExample.class, "--optionOne", "Value2");

        assertThat(cli.getOptionOne(), equalTo(ExampleEnum.Value2));
    }

    @Test public void specifiedMultivaluedEnumOptionGivesListOfEnumValues() {
        final EnumOptionExample cli =
                CliFactory.parseArguments(EnumOptionExample.class, "--optionTwo", "Value1", "Value3");

        assertThat(cli.getOptionTwo(), hasItems(ExampleEnum.Value1, ExampleEnum.Value3));
    }
}
