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

import java.lang.reflect.Method;

class OptionsSpecificationParser<O>
{
   private final Class<O> m_klass;

   OptionsSpecificationParser(final Class<O> klass)
   {
      m_klass = klass;
   }

   void buildOptionsSpecification(final OptionsSpecificationBuilder builder)
   {
      final Method[] methods = m_klass.getMethods();
      for (final Method method : methods)
      {
         if(!Void.class.equals(method.getReturnType()))
         {
            if(method.isAnnotationPresent(Option.class) || method.isAnnotationPresent(Unparsed.class))
            {
               new OptionSpecificationParser(m_klass, method).buildOptionSpecification(builder);
            }
         }
      }

      if(m_klass.isAnnotationPresent(CommandLineInterface.class))
      {
         final CommandLineInterface klassAnnotation = m_klass.getAnnotation(CommandLineInterface.class);
         builder.setApplicationName(klassAnnotation.application());
      }
   }
}
