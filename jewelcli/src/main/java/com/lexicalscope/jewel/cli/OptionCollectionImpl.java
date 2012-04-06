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

package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public boolean hasUnparsed()
    {
        return !unparsed.isEmpty();
    }

    public List<String> getUnparsed()
    {
        return new ArrayList<String>(unparsed);
    }

    public Iterator<Argument> iterator()
    {
        return new Iterator<Argument>() {
            final Iterator<Map.Entry<ParsedOptionSpecification, List<String>>> m_iterator = arguments.entrySet().iterator();

            public boolean hasNext()
            {
                return m_iterator.hasNext();
            }

            public Argument next()
            {
                final Entry<ParsedOptionSpecification, List<String>> next = m_iterator.next();
                return new ArgumentImpl(next.getKey(), next.getValue());
            }

            public void remove()
            {
                m_iterator.remove();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAny(final List<String> options)
    {
        for (final String option : options)
        {
            if (arguments.containsKey(option))
            {
                return true;
            }
        }
        return false;
    }

    @Override public Argument getArgument(final List<String> options) {
        for (final String option : options) {
            final ParsedOptionSpecification optionSpecification = specification.getSpecification(option);
            if (arguments.containsKey(optionSpecification)) {
                return new ArgumentImpl(optionSpecification, arguments.get(optionSpecification));
            }
        }
        return null;
    }

    List<String> getValues(final String... options)
    {
        return getValues(Arrays.asList(options));
    }

    private List<String> getValues(final List<String> options)
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

//    @Override public void forEach(final ArgumentProcessor argumentProcessor) {
//        final Set<Entry<String, List<String>>> entrySet = arguments.entrySet();
//        final Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
//        while (iterator.hasNext()) {
//            final Map.Entry<java.lang.String, java.util.List<java.lang.String>> entry = iterator.next();
//
//            if(iterator.hasNext()) {
//                argumentProcessor.processOption(entry.getKey(), entry.getValue());
//            } else {
//                argumentProcessor.processLastOption(entry.getKey(), entry.getValue());
//            }
//        }
//        argumentProcessor.finishedProcessing(unparsed);
//    }
}
