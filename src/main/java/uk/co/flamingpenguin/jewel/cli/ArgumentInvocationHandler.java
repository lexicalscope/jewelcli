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
/*
 * Copyright 2009 Tim Wood
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

import static com.lexicalscope.fluentreflection.FluentReflection.method;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

final class ArgumentInvocationHandler<O> implements InvocationHandler {
    private final OptionsSpecification<O> m_specification;
    private final Class<O> m_klass;
    private final TypedArguments m_arguments;

    ArgumentInvocationHandler(final Class<O> klass,
                             final OptionsSpecification<O> specification,
                             final TypedArguments arguments) {
        m_klass = klass;
        m_specification = specification;
        m_arguments = arguments;
    }

    /**
     * {@inheritdoc}
     */
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (args != null && args.length != 0) {
            throw new UnsupportedOperationException(String.format(
                    "Method (%s) with arguments not supported for reading argument values",
                    method.toGenericString()));
        }

        if (m_specification.isUnparsedMethod(method)) {
            return m_arguments.getUnparsedValue();
        } else if (m_specification.isUnparsedExistenceChecker(method(method))) {
            return m_arguments.hasUnparsedValue();
        } else if (!m_specification.isSpecified(method)) {
            throw new UnsupportedOperationException(String.format(
                    "Method (%s) is not annotated for option specification",
                    method.toGenericString()));
        }

        final OptionSpecification specification = m_specification.getSpecification(method);

        if (m_specification.isExistenceChecker(method(method)) || !specification.hasValue()) {
            return optionPresent(m_arguments, specification);
        }
        return getValue(m_arguments, method, specification);
    }

    private Object optionPresent(final TypedArguments arguments, final OptionSpecification specification) {
        return arguments.contains(specification);
    }

    private Object getValue(final TypedArguments arguments, final Method method, final OptionSpecification specification)
            throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        final Object value = arguments.getValue(specification);
        if (value == null) {
            throw new OptionNotPresentException(specification);
        }
        return value;
    }

    /**
     * {@inheritdoc}
     */
    @Override public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("Option values for ")
                .append(m_klass.getCanonicalName())
                .append(":");

        final List<String> values = new ArrayList<String>();
        for (final OptionSpecification methodSpecification : m_specification) {
            if (methodSpecification.isHelpOption()) {
                continue;
            } else if (m_arguments.contains(methodSpecification)) {
                final StringBuilder valueString = new StringBuilder();

                valueString.append("--")
                        .append(methodSpecification.getLongName());

                if (methodSpecification.hasValue()) {
                    final Object value = m_arguments.getValue(methodSpecification);
                    valueString.append(" ");
                    valueString.append(formatValue(value));
                }
                values.add(valueString.toString());
            }
        }
        if (m_specification.hasUnparsedSpecification() && m_arguments.hasUnparsedValue()) {
            values.add(formatValue(m_arguments.getUnparsedValue()));
        }

        if (!values.isEmpty()) {
            result.append(" ").append(join(values));
        }

        return result.toString();
    }

    private String formatValue(final Object value) {
        if (value instanceof Iterable<?>) {
            final Iterable<?> valueIterable = (Iterable<?>) value;

            return join(valueIterable);
        } else {
            return "" + value;
        }
    }

    private String join(final Iterable<?> valueIterable) {
        final StringBuilder result = new StringBuilder();

        String seperator = "";
        for (final Object object : valueIterable) {
            result.append(seperator).append(object);
            seperator = " ";
        }
        return result.toString();
    }
}