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

import java.util.ArrayList;
import java.util.List;

class OptionUtils
{
   static List<String> getAllNames(final OptionSpecification specification)
   {
      final List<String> result = new ArrayList<String>();
      if(specification.hasShortName())
      {
         result.addAll(specification.getShortNames());
      }
      result.add(specification.getLongName());
      return result;
   }
}
