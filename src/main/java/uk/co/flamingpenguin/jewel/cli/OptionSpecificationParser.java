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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


class OptionSpecificationParser
{
   private static final Logger g_logger = Logger.getLogger(OptionSpecificationParser.class.getName());

   private final Method m_method;
   private final Class<?> m_klass;

   public OptionSpecificationParser(final Class<?> klass, final Method method)
   {
      m_klass = klass;
      m_method = method;
   }

   OptionSpecificationImpl buildOptionSpecification(final OptionsSpecificationBuilder builder)
   {
      final OptionSpecificationBuilder optionSpecificationBuilder = new OptionSpecificationBuilder(m_method);

      final Type returnType = m_method.getGenericReturnType();
      final Class<?> type = (Class<?>) (isList(m_method.getReturnType()) ? getListType(returnType) : returnType);
      optionSpecificationBuilder.setType(type);

      final boolean multiValued = isList(m_method.getReturnType());
      optionSpecificationBuilder.setMultiValued(multiValued);

      final String baseName = extractBaseMethodName(m_method);
      final Method optionalityMethod = findCorrespondingOptionalityMethod(baseName, m_klass);
      if(optionalityMethod != null)
      {
         optionSpecificationBuilder.setOptionalityMethod(optionalityMethod);
      }

      if(m_method.isAnnotationPresent(Option.class))
      {
         final Option optionAnnotation = m_method.getAnnotation(Option.class);

         final String[] shortNameSpecification = optionAnnotation.shortName();
         final List<String> shortNames = new ArrayList<String>();
         for (final String element : shortNameSpecification)
         {
            final String shortName = element.trim();
            if(shortName.length() > 0)
            {
               shortNames.add(element.substring(0, 1));
            }
         }
         optionSpecificationBuilder.setShortNames(shortNames);

         final String longNameSpecification = optionAnnotation.longName().trim();
         final String longName = nullOrBlank(longNameSpecification) ? baseName : longNameSpecification;
         optionSpecificationBuilder.setLongName(longName);

         final String description = optionAnnotation.description().trim();
         optionSpecificationBuilder.setDescription(description);

         final String pattern = optionAnnotation.pattern();
         optionSpecificationBuilder.setPattern(pattern);

         final List<String> defaultValue = Arrays.asList(optionAnnotation.defaultValue());
         optionSpecificationBuilder.setDefaultValue(defaultValue);

         final boolean helpRequest = optionAnnotation.helpRequest();
         optionSpecificationBuilder.setHelpRequest(helpRequest);

         builder.addOption(optionSpecificationBuilder.createOptionSpecification());
      }
      else if (m_method.isAnnotationPresent(Unparsed.class))
      {
         final Unparsed annotation = m_method.getAnnotation(Unparsed.class);

         optionSpecificationBuilder.setLongName(annotation.name());
         optionSpecificationBuilder.setDescription("");
         optionSpecificationBuilder.setPattern(".*");
         optionSpecificationBuilder.setDefaultValue(Collections.<String>emptyList());
         optionSpecificationBuilder.setHelpRequest(false);

         builder.addUnparsedOption(optionSpecificationBuilder.createOptionSpecification());
      }

      return optionSpecificationBuilder.createOptionSpecification();
   }

   private boolean nullOrBlank(final String string)
   {
      return string == null || string.equals("");
   }

   private final boolean isList(final Class<?> klass)
   {
      return klass.isAssignableFrom(List.class);
   }

   private final Class<?> getListType(final Type genericReturnType)
   {
      if(genericReturnType instanceof ParameterizedType)
      {
         return (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
      }
      else
      {
         g_logger.finer("Found raw List type; assuming List<String>.");
         return String.class;
      }
   }

   private final Method findCorrespondingOptionalityMethod(final String name, final Class<?> klass)
   {
      try
      {
         final Method method = klass.getMethod(addPrefix("is", name), new Class[]{});
         if(isBoolean(method.getReturnType()))
         {
            return method;
         }
         return null;
      }
      catch (final NoSuchMethodException e)
      {
         return null;
      }
   }

   private String addPrefix(final String prefix, final String name)
   {
      return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
   }

   private String extractBaseMethodName(final Method method)
   {
      final String methodName = method.getName();

      final String isPrefix = "is";
      if(isBoolean(method.getReturnType()) && methodName.startsWith(isPrefix))
      {
         return stripPrefix(methodName, isPrefix);
      }
      return stripPrefix(methodName, "get");
   }

   private final boolean isBoolean(final Class<?> type)
   {
      return (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class));
   }

   private final String stripPrefix(final String methodName, final String prefix)
   {
      if(methodName.length() > prefix.length() && methodName.startsWith(prefix))
      {
         return methodName.substring(prefix.length(), prefix.length() + 1).toLowerCase()
                + ((methodName.length() > prefix.length()+1) ? methodName.substring(prefix.length() + 1) : "");
      }
      return methodName;
   }
}
