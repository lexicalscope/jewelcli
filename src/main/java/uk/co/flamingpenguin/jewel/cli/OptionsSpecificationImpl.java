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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


class OptionsSpecificationImpl<O> implements OptionsSpecification<O>
{
   private final LinkedHashMap<String, OptionSpecificationImpl> m_optionsShortName = new LinkedHashMap<String, OptionSpecificationImpl>();
   private final LinkedHashMap<String, OptionSpecificationImpl> m_optionsLongName = new LinkedHashMap<String, OptionSpecificationImpl>();
   private final LinkedHashMap<Method, OptionSpecificationImpl> m_optionsMethod = new LinkedHashMap<Method, OptionSpecificationImpl>();
   private final LinkedHashMap<Method, OptionSpecificationImpl> m_optionalOptionsMethod = new LinkedHashMap<Method, OptionSpecificationImpl>();

   public OptionsSpecificationImpl(final Class<O> klass)
   {
      final Method[] declaredMethods = klass.getDeclaredMethods();
      for (final Method method : declaredMethods)
      {
         if(method.isAnnotationPresent(Option.class) && !Void.class.equals(method.getReturnType()))
         {
            final OptionSpecificationImpl optionSpecification = new OptionSpecificationImpl(method, klass);
            if(optionSpecification.hasShortName())
            {
               m_optionsShortName.put(optionSpecification.getShortName(), optionSpecification);
            }
            m_optionsLongName.put(optionSpecification.getLongName(), optionSpecification);
            m_optionsMethod.put(method, optionSpecification);

            if(optionSpecification.isOptional())
            {
               m_optionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
            }
         }
      }
   }

   /**
    * @inheritdoc
    */
   public boolean isSpecified(final String key)
   {
      return m_optionsShortName.containsKey(key) || m_optionsLongName.containsKey(key);
   }

   /**
    * @inheritdoc
    */
   public boolean isSpecified(final Method method)
   {
      return m_optionsMethod.containsKey(method) || m_optionalOptionsMethod.containsKey(method);
   }

   /**
    * @inheritdoc
    */
   public OptionSpecification getSpecification(final String key)
   {
      if(m_optionsLongName.containsKey(key))
      {
         return m_optionsLongName.get(key);
      }
      else
      {
         return m_optionsShortName.get(key);
      }
   }

   /**
    * @inheritdoc
    */
   public OptionSpecification getSpecification(final Method method)
   {
      if(m_optionsMethod.containsKey(method))
      {
         return m_optionsMethod.get(method);
      }
      return m_optionalOptionsMethod.get(method);
   }

   /**
    * @inheritdoc
    */
   public List<OptionSpecification> getManditoryOptions()
   {
      final ArrayList<OptionSpecification> result = new ArrayList<OptionSpecification>();
      for (OptionSpecificationImpl specification : m_optionsLongName.values())
      {
         if(!specification.isOptional())
         {
            result.add(specification);
         }
      }

      return result;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      String separator = "";
      for (final OptionSpecification specification : m_optionsLongName.values())
      {
         result.append(separator);
         specification.getSummary(result);
         separator = " ";
      }

      for (final OptionSpecification specification : m_optionsLongName.values())
      {
         result.append("\t").append(System.getProperty("line.separator")).append(specification);
      }

      return result.toString();
   }

   public boolean isExistenceChecker(final Method method)
   {
      return m_optionalOptionsMethod.containsKey(method);
   }

   public Iterator<OptionSpecification> iterator()
   {
      return new ArrayList<OptionSpecification>(m_optionsMethod.values()).iterator();
   }
}
