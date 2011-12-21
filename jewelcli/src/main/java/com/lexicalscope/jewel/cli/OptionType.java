/*
 * Copyright 2009 Tim Wood
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

class OptionType
{
   private final Class<?> m_type;
   private final String m_pattern;
   private final boolean m_multiValued;

   OptionType(final Class<?> type, final String pattern, final boolean multiValued)
   {
      m_type = type;
      m_pattern = pattern;
      m_multiValued = multiValued;
   }

   Class<?> getType()
   {
      return m_type;
   }

   String getPattern()
   {
      return m_pattern;
   }

   boolean isMultiValued()
   {
      return m_multiValued;
   }
}
