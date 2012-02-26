package com.lexicalscope.jewelcli.JewelCliPackagingTest;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
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

public class TestCliParsing {
    interface MyOptions
    {
        @Option Integer getMyOption();
    }

    @Test public void parsingWorks() throws ArgumentValidationException
    {
        final MyOptions parsedArguments =
                CliFactory.parseArguments(MyOptions.class, new String[] { "--myOption", "14" });
        AssertJUnit.assertEquals((Integer) 14, parsedArguments.getMyOption());
    }

    @Test public void validationWorks()
    {
        try {

            CliFactory.parseArguments(MyOptions.class, new String[] {});
            AssertJUnit.fail();
        } catch (final ArgumentValidationException e) {
            AssertJUnit.assertEquals(
                    e.getValidationErrors().get(0).getErrorType(),
                    ArgumentValidationException.ValidationError.ErrorType.MissingOption);
        }
    }
}
