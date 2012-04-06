/*
 * Copyright 2006 Tim Wood
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

package com.lexicalscope.jewel.cli.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;


class OptionCollectionImpl implements OptionCollection
{
    private final OptionsSpecification<?> specification;
    private final Map<ParsedOptionSpecification, List<String>> arguments;
    private final List<String> unparsed;

    public OptionCollectionImpl(
            final OptionsSpecification<?> specification,
            final Map<ParsedOptionSpecification,
            List<String>> arguments,
            final List<String> unparsed)
    {
        this.specification = specification;
        this.arguments = arguments;
        this.unparsed = unparsed;
    }

    @Override public List<String> getUnparsed()
    {
        return new ArrayList<String>(unparsed);
    }

    @Override public Argument getArgument(final ParsedOptionSpecification option) {
        if (arguments.containsKey(option)) {
            return new ArgumentImpl(option, arguments.get(option));
        }
        return null;
    }

    @Override public List<String> getValues(final String... options)
    {
        for (final String option : options)
        {
            final ParsedOptionSpecification optionSpecification = specification.getSpecification(option);
            if (arguments.containsKey(optionSpecification))
            {
                return arguments.get(optionSpecification);
            }
        }
        return null;
    }
}
