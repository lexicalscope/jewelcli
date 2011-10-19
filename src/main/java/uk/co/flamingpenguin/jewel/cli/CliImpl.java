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

class CliImpl<O> implements Cli<O> {
    private final OptionsSpecificationImpl<O> m_specification;
    private final Class<O> m_klass;

    public CliImpl(final Class<O> klass) {
        final OptionsSpecificationImpl<O> specification =
                OptionsSpecificationImpl.<O>createOptionsSpecificationImpl(klass);

        final OptionsSpecificationParser<O> optionsSpecificationParser = new OptionsSpecificationParser<O>(klass);
        optionsSpecificationParser.buildOptionsSpecification(specification);

        m_specification = specification;
        m_klass = klass;
    }

    /**
     * @inheritdoc
     */
    public O parseArguments(final String... arguments) throws ArgumentValidationException {
        final ArgumentCollection validatedArguments =
                new ArgumentValidatorImpl<O>(m_specification).validateArguments(new ParsedArgumentsBuilder()
                        .parseArguments(arguments));

        return new ArgumentPresenterImpl<O>(m_klass, m_specification).presentArguments(validatedArguments);
    }

    /**
     * @inheritdoc
     */
    public String getHelpMessage() {
        return m_specification.toString();
    }

    /**
     * {@inheritdoc}
     */
    public CliSpecification getSpecification() {
        return m_specification;
    }
}
