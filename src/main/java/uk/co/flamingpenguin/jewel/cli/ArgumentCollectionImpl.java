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

package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


class ArgumentCollectionImpl implements ArgumentCollection
{
   private final Map<String, List<String>> m_arguments;
   private final List<String> m_unparsed;

   public ArgumentCollectionImpl(final Map<String, List<String>> arguments, final List<String> unparsed)
   {
      m_arguments = arguments;
      m_unparsed = unparsed;
   }

   public boolean hasUnparsed()
   {
      return !m_unparsed.isEmpty();
   }

   public List<String> getUnparsed()
   {
      return new ArrayList<String>(m_unparsed);
   }

   public Iterator<Argument> iterator()
   {
      return new Iterator<Argument>() {
         final Iterator<Map.Entry<String, List<String>>> m_iterator = m_arguments.entrySet().iterator();

         public boolean hasNext()
         {
            return m_iterator.hasNext();
         }

         public Argument next()
         {
            final Entry<String, List<String>> next = m_iterator.next();
            return new ArgumentImpl(next.getKey(), next.getValue());
         }

         public void remove()
         {
            m_iterator.remove();
         }};
   }

   boolean containsAny(final String... options)
   {
      return containsAny(Arrays.asList(options));
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsAny(final List<String> options)
   {
      for (final String option : options)
      {
         if(m_arguments.containsKey(option))
         {
            return true;
         }
      }
      return false;
   }

   List<String> getValues(final String... options)
   {
      return getValues(Arrays.asList(options));
   }

   /**
    * {@inheritDoc}
    */
   public List<String> getValues(final List<String> options)
   {
      for (final String option : options)
      {
         if(m_arguments.containsKey(option))
         {
            return m_arguments.get(option);
         }
      }
      return null;
   }
}
