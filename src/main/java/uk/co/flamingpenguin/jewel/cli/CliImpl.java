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


class CliImpl<O> implements Cli<O>
{
   private final OptionsSpecification<O> m_specification;
   private final Class<O> m_klass;

   public CliImpl(final Class<O> klass)
   {
      m_specification = new OptionsSpecificationImpl<O>(klass);
      m_klass = klass;
   }

   /**
    * @inheritdoc
    */
   public O parseArguments(final String[] arguments) throws ArgumentValidationException
   {
      final ValidatedArguments validatedArguments = new ArgumentValidatorImpl<O>(m_specification).validateArguments(new ArgumentParserImpl(arguments).parseArguments());
      final TypedArguments typedArguments = new ArgumentTyperImpl<O>(m_specification).typeArguments(validatedArguments);
      return new ArgumentPresenterImpl<O>(m_klass, m_specification).presentArguments(typedArguments);
   }
}
