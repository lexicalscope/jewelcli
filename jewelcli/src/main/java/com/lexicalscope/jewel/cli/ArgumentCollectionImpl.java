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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lexicalscope.jewel.cli.arguments.ArgumentProcessor;


class ArgumentCollectionImpl implements ArgumentCollection
{
    private final Map<String, List<String>> m_arguments;
    private final List<String> m_unparsed;

    public ArgumentCollectionImpl(final Map<String, List<String>> arguments, final List<String> unparsed)
    {
        m_arguments = arguments;
        m_unparsed = unparsed;
    }

    public List<String> getUnparsed()
    {
        return new ArrayList<String>(m_unparsed);
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAny(final List<String> options)
    {
        for (final String option : options)
        {
            if (m_arguments.containsKey(option))
            {
                return true;
            }
        }
        return false;
    }

    @Override public void forEach(final ArgumentProcessor argumentProcessor) {
        final Set<Entry<String, List<String>>> entrySet = m_arguments.entrySet();
        final Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            final Map.Entry<java.lang.String, java.util.List<java.lang.String>> entry = iterator.next();

            if(iterator.hasNext()) {
                argumentProcessor.processOption(entry.getKey(), entry.getValue());
            } else {
                argumentProcessor.processLastOption(entry.getKey(), entry.getValue());
            }
        }
        argumentProcessor.finishedProcessing(m_unparsed);
    }
}
