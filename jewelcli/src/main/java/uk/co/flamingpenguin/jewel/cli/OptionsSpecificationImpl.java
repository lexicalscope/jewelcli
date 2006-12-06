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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


class OptionsSpecificationImpl<O> implements OptionsSpecification<O>
{
   private final Map<String, OptionSpecificationImpl> m_optionsShortName = new HashMap<String, OptionSpecificationImpl>();
   private final Map<String, OptionSpecificationImpl> m_optionsLongName = new TreeMap<String, OptionSpecificationImpl>();
   private final Map<Method, OptionSpecificationImpl> m_optionsMethod = new HashMap<Method, OptionSpecificationImpl>();
   private final Map<Method, OptionSpecificationImpl> m_optionalOptionsMethod = new HashMap<Method, OptionSpecificationImpl>();
   private UnparsedSpecificationImpl m_unparsed = null;
   private CliSpecificationImpl m_cliSpecification = null;

   public OptionsSpecificationImpl(final Class<O> klass)
   {
      final Method[] methods = klass.getMethods();
      for (final Method method : methods)
      {
         if(!Void.class.equals(method.getReturnType()))
         {
            if(method.isAnnotationPresent(Option.class))
            {
               final OptionSpecificationImpl optionSpecification = new OptionSpecificationImpl(method, klass);

               for (final String shortName : optionSpecification.getShortNames())
               {
                  m_optionsShortName.put(shortName, optionSpecification);
               }

               m_optionsLongName.put(optionSpecification.getLongName(), optionSpecification);
               m_optionsMethod.put(method, optionSpecification);

               if(optionSpecification.isOptional())
               {
                  m_optionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
               }
            }
            else if (method.isAnnotationPresent(Unparsed.class))
            {
               m_unparsed = new UnparsedSpecificationImpl(method, klass);
            }
         }
      }
      m_cliSpecification = new CliSpecificationImpl(klass.getAnnotation(CommandLineInterface.class), m_unparsed, !getMandatoryOptions().isEmpty());
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
   public List<OptionSpecification> getMandatoryOptions()
   {
      final ArrayList<OptionSpecification> result = new ArrayList<OptionSpecification>();
      for (OptionSpecificationImpl specification : m_optionsLongName.values())
      {
         if(!specification.isOptional() && !specification.hasDefaultValue())
         {
            result.add(specification);
         }
      }

      return result;
   }

   @Override
   public String toString()
   {
      final String lineSeparator = System.getProperty("line.separator");
      final StringBuilder result = new StringBuilder();
      result.append(m_cliSpecification).append(lineSeparator);

      String separator = "";
      for (final ArgumentSpecification specification : m_optionsLongName.values())
      {
         result.append(separator).append("\t").append(specification);
         separator = lineSeparator;
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

   public ArgumentSpecification getUnparsedSpecification()
   {
      return m_unparsed;
   }

   public boolean hasUnparsedSpecification()
   {
      return m_unparsed != null;
   }
}
