package com.lexicalscope.jewel.cli.examples;

import static com.lexicalscope.jewel.cli.CliFactory.createCli;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

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

public class TestHelpMessages {
    interface OptionWithDefault
    {
        @Option(defaultValue = "3")
        int getMyOption();

        @Option(helpRequest = true)
        boolean getHelp();
    }

    @Test public void optionWithDefaultShowAsOptional()
    {
        assertThat(createCli(OptionWithDefault.class).getHelpMessage(), containsString("[--myOption value]"));
    }

    interface HiddenOptionNotIncludedInHelp
    {
        @Option(hidden = true)
        int getMyOption();
    }

    @Test public void hiddenOptionNotIncludedInHelp()
    {
        assertThat(createCli(HiddenOptionNotIncludedInHelp.class).getHelpMessage(), not(containsString("myOption")));
    }

    interface HiddenUnparsedOptionNotIncludedInHelp
    {
        @Unparsed(hidden = true)
        int getMyOption();
    }

    @Test public void hiddenUnparsedOptionNotIncludedInHelp()
    {
        assertThat(createCli(HiddenUnparsedOptionNotIncludedInHelp.class).getHelpMessage(), not(containsString("ARGUMENTS")));
    }
}
