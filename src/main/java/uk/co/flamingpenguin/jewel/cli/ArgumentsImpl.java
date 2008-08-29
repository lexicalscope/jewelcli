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


class ArgumentsImpl implements ValidatedArguments
{
   private final Map<String, List<String>> m_arguments;
   private final List<String> m_unparsed;

   public ArgumentsImpl(final Map<String, List<String>> arguments, final List<String> unparsed)
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

   public Iterator<Entry<String, List<String>>> iterator()
   {
      return m_arguments.entrySet().iterator();
   }

   public boolean contains(final String... options)
   {
      return containsAny(Arrays.asList(options));
   }

   public boolean containsAny(List<String> options)
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

   public List<String> getValues(final String... options)
   {
      return getValues(Arrays.asList(options));
   }

   public List<String> getValues(List<String> options)
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
