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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


class ArgumentValidatorImpl<O> implements ArgumentValidator<O>
{
   private final ValidationErrorBuilder m_validationErrorBuilder;
   private final List<String> m_validatedUnparsedArguments;
   private final Map<String, List<String>> m_validatedArguments;
   private final OptionsSpecification<O> m_specification;

   public ArgumentValidatorImpl(OptionsSpecification<O> specification)
   {
      this.m_specification = specification;

      m_validatedArguments = new LinkedHashMap<String, List<String>>();
      m_validatedUnparsedArguments = new ArrayList<String>();

      m_validationErrorBuilder = new ValidationErrorBuilderImpl();
   }

   /**
    * @inheritdoc
    */
   public ValidatedArguments validateArguments(final ArgumentCollection arguments) throws ArgumentValidationException
   {
      m_validatedUnparsedArguments.addAll(arguments.getUnparsed());

      final Iterator<Entry<String, List<String>>> argumentsIterator = arguments.iterator();
      while (argumentsIterator.hasNext())
      {
         final Entry<String, List<String>> entry = argumentsIterator.next();
         boolean isLast = !argumentsIterator.hasNext();

         if(!m_specification.isSpecified(entry.getKey()))
         {
            m_validationErrorBuilder.unexpectedOption(entry.getKey());
         }
         else
         {
            final OptionMethodSpecification optionSpecification = m_specification.getSpecification(entry.getKey());
            if(optionSpecification.isHelpOption())
            {
               m_validationErrorBuilder.helpRequested(m_specification);
            }
            else if(entry.getValue().size() == 0 && optionSpecification.hasValue() && !optionSpecification.isMultiValued())
            {
               m_validationErrorBuilder.missingValue(optionSpecification);
            }
            else if(!isLast && entry.getValue().size() > 0 && !optionSpecification.hasValue())
            {
               m_validationErrorBuilder.unexpectedValue(optionSpecification);
            }
            else if(!isLast && entry.getValue().size() > 1 && !optionSpecification.isMultiValued())
            {
               m_validationErrorBuilder.unexpectedAdditionalValues(optionSpecification);
            }

            if(isLast && hasExcessValues(entry, optionSpecification))
            {
               final List<String> values = new ArrayList<String>();
               final List<String> unparsed;
               if(optionSpecification.hasValue())
               {
                  values.add(entry.getValue().get(0));
                  unparsed = new ArrayList<String>(entry.getValue().subList(1, entry.getValue().size()));
               }
               else
               {
                  unparsed = entry.getValue();
               }

               m_validatedArguments.put(entry.getKey(), values);
               m_validatedUnparsedArguments.addAll(0, unparsed);
            }
            else
            {
               checkAndAddValues(optionSpecification, entry.getKey(), new ArrayList<String>(entry.getValue()));
            }
         }
      }

      for(final OptionMethodSpecification optionSpecification : m_specification.getMandatoryOptions())
      {
         if(!(arguments.containsAny(optionSpecification.getAllNames())))
         {
            m_validationErrorBuilder.missingOption(optionSpecification);
         }
      }
      
      validateUnparsedOptions();

      m_validationErrorBuilder.validate();

      return new ArgumentsImpl(m_validatedArguments, m_validatedUnparsedArguments);
   }

   private void validateUnparsedOptions()
   {
      if(m_specification.hasUnparsedSpecification())
      {
         final ArgumentMethodSpecification argumentSpecification = m_specification.getUnparsedSpecification();
         if(!argumentSpecification.isOptional() &&
              (m_validatedUnparsedArguments.isEmpty() || 
                 (argumentSpecification.isMultiValued() && m_validatedArguments.size() == 1)))
         {
            m_validationErrorBuilder.missingValue(argumentSpecification);
         }
      }
   }

   private boolean hasExcessValues(final Entry<String, List<String>> entry, final OptionMethodSpecification optionSpecification)
   {
      return (!optionSpecification.isMultiValued()
                && (entry.getValue().size() > 1 || (entry.getValue().size() > 0 && !optionSpecification.hasValue())));
   }

   private void checkAndAddValues(final OptionMethodSpecification optionSpecification, final String option, final ArrayList<String> values)
   {
      for (final String value : values)
      {
         if(!optionSpecification.patternMatches(value))
         {
            m_validationErrorBuilder.patternMismatch(optionSpecification, value);
         }
      }
      m_validatedArguments.put(option, new ArrayList<String>(values));
   }
}
