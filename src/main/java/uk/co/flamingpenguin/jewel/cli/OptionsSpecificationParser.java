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

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import com.lexicalscope.fluentreflection.FluentReflection;
import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class OptionsSpecificationParser<O> {
    private final ReflectedClass<O> m_klass;

    OptionsSpecificationParser(final Class<O> klass) {
        m_klass = FluentReflection.type(klass);
    }

    void buildOptionsSpecification(final OptionsSpecificationBuilder builder) {
        for (final ReflectedMethod method : m_klass.methods(isQuery().and(annotatedWith(Option.class).or(
                annotatedWith(Unparsed.class))))) {
            new OptionSpecificationParser(m_klass, method).buildOptionSpecification(builder);
        }

        if (m_klass.classUnderReflection().isAnnotationPresent(CommandLineInterface.class)) {
            final CommandLineInterface klassAnnotation =
                    m_klass.classUnderReflection().getAnnotation(CommandLineInterface.class);
            builder.setApplicationName(klassAnnotation.application());
        }
    }
}
