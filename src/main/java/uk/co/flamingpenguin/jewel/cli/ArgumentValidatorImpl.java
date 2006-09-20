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
   private final OptionsSpecification<O> m_specification;

   public ArgumentValidatorImpl(OptionsSpecification<O> specification)
   {
      this.m_specification = specification;
   }

   /**
    * @inheritdoc
    */
   public ValidatedArguments validateArguments(final ParsedArguments arguments) throws ArgumentValidationException
   {
      final ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilderImpl();

      for(final OptionSpecification optionSpecification : m_specification.getManditoryOptions())
      {
         if(!arguments.contains(optionSpecification.getShortName(), optionSpecification.getLongName()))
         {
            validationErrorBuilder.missingOption(optionSpecification);
         }
      }
 
      final Map<String, List<String>> validatedArguments = new LinkedHashMap<String, List<String>>();
      final List<String> validatedUnparsedArguments = new ArrayList<String>(arguments.getUnparsed());
      
      final Iterator<Entry<String, List<String>>> argumentsIterator = arguments.iterator();
      while (argumentsIterator.hasNext())
      {
         final Entry<String, List<String>> entry = argumentsIterator.next();
         boolean isLast = !argumentsIterator.hasNext();

         if(!m_specification.isSpecified(entry.getKey()))
         {
            validationErrorBuilder.unexpectedOption(entry.getKey());
         }
         else 
         {
            final OptionSpecification optionSpecification = m_specification.getSpecification(entry.getKey());
            if(entry.getValue().size() == 0 && optionSpecification.hasValue() && !optionSpecification.isMultiValued())
            {
               validationErrorBuilder.missingValue(optionSpecification);
            }
            else if(entry.getValue().size() > 0 && !optionSpecification.hasValue())
            {
               validationErrorBuilder.unexpectedValue(optionSpecification);
            }
            else if(!isLast && entry.getValue().size() > 1 && !optionSpecification.isMultiValued())
            {
               validationErrorBuilder.unexpectedAdditionalValues(optionSpecification);
            }
            
            if(isLast && !optionSpecification.isMultiValued() && entry.getValue().size() > 1)
            {
               final ArrayList<String> values = new ArrayList<String>();
               values.add(entry.getValue().get(0));
               validatedArguments.put(entry.getKey(), values);
               
               final ArrayList<String> unparsed = new ArrayList<String>(entry.getValue().subList(1, entry.getValue().size()));
               validatedUnparsedArguments.addAll(0, unparsed);
            }
            else
            {
               validatedArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
            }
         }
      }

      validationErrorBuilder.validate();
      
      return new ArgumentsImpl(validatedArguments, validatedUnparsedArguments);
   }
}
