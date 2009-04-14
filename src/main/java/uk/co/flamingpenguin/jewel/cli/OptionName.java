/*
 * Copyright 2007 Tim Wood
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

import java.util.List;

class OptionName
{
   private final String m_longName;
   private final List<String> m_shortNames;
   private final String m_description;

   OptionName(final String longName, final List<String> shortNames, final String description)
   {
      m_longName = longName;
      m_shortNames = shortNames;
      m_description = description;
   }

   String getLongName()
   {
      return m_longName;
   }

   List<String> getShortNames()
   {
      return m_shortNames;
   }

   String getDescription()
   {
      return m_description;
   }
}
