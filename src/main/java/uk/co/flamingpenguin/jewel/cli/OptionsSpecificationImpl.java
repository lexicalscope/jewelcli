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

import static com.lexicalscope.fluentreflection.FluentReflection.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class OptionsSpecificationImpl<O> implements OptionsSpecification<O>, CliSpecification {
    private final ReflectedClass<O> klass;

    private final SortedSet<OptionSpecification> options = new TreeSet<OptionSpecification>();
    private final Map<String, OptionSpecification> optionsByName = new HashMap<String, OptionSpecification>();
    private final Map<Method, OptionSpecification> optionsMethod = new HashMap<Method, OptionSpecification>();
    private final Map<ReflectedMethod, OptionSpecification> optionalOptionsMethod =
            new HashMap<ReflectedMethod, OptionSpecification>();

    private final Map<Method, OptionSpecification> unparsedOptionsMethod = new HashMap<Method, OptionSpecification>();
    private final Map<ReflectedMethod, OptionSpecification> unparsedOptionalOptionsMethod =
            new HashMap<ReflectedMethod, OptionSpecification>();

    OptionsSpecificationImpl(
            final ReflectedClass<O> klass,
            final List<OptionSpecification> optionSpecifications,
            final List<OptionSpecification> unparsedSpecifications) {
        this.klass = klass;

        for (final OptionSpecification optionSpecification : optionSpecifications) {
            addOption(optionSpecification);
        }

        for (final OptionSpecification optionSpecification : unparsedSpecifications) {
            addUnparsedOption(optionSpecification);
        }
    }

    static <O> OptionsSpecification<O> createOptionsSpecificationImpl(final ReflectedClass<O> klass) {
        return new OptionsSpecificationParser<O>(klass).buildOptionsSpecification();
    }

    @Override public boolean isSpecified(final String key) {
        return optionsByName.containsKey(key);
    }

    @Override public OptionSpecification getSpecification(final String key) {
        return optionsByName.get(key);
    }

    @Override public OptionSpecification getSpecification(final ReflectedMethod reflectedMethod) {
        return getSpecification(reflectedMethod.methodUnderReflection());
    }

    private OptionSpecification getSpecification(final Method method) {
        if (optionsMethod.containsKey(method)) {
            return optionsMethod.get(method);
        }
        return optionalOptionsMethod.get(method(method));
    }

    @Override public List<OptionSpecification> getMandatoryOptions() {
        final List<OptionSpecification> result = new ArrayList<OptionSpecification>();
        for (final OptionSpecification specification : options) {
            if (!specification.isOptional() && !specification.hasDefaultValue()) {
                result.add(specification);
            }
        }

        return result;
    }

    @Override public Iterator<OptionSpecification> iterator() {
        return new ArrayList<OptionSpecification>(optionsMethod.values()).iterator();
    }

    @Override public OptionSpecification getUnparsedSpecification() {
        return unparsedOptionsMethod.values().iterator().next();
    }

    @Override public boolean hasUnparsedSpecification() {
        return !unparsedOptionsMethod.values().isEmpty();
    }

    @Override public String getApplicationName() {
        final String applicationName = applicationName();
        if (applicationName != null)
        {
            return applicationName;
        }
        return klass.name();
    }

    private String applicationName() {
        if (klass.annotatedWith(CommandLineInterface.class))
        {
            final String applicationName = klass.annotation(CommandLineInterface.class).application();
            if (applicationName != null && !applicationName.trim().equals("")) {
                return applicationName.trim();
            }
        }
        return null;
    }

    private void addOption(final OptionSpecification optionSpecification) {
        for (final String shortName : optionSpecification.getShortNames()) {
            optionsByName.put(shortName, optionSpecification);
        }
        optionsByName.put(optionSpecification.getLongName(), optionSpecification);
        options.add(optionSpecification);

        optionsMethod.put(optionSpecification.getMethod(), optionSpecification);

        if (optionSpecification.isOptional()) {
            optionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
        }
    }

    private void addUnparsedOption(final OptionSpecification optionSpecification) {
        unparsedOptionsMethod.put(optionSpecification.getMethod(), optionSpecification);

        if (optionSpecification.isOptional()) {
            unparsedOptionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
        }
    }

    @Override public String toString() {
        final StringBuilder message = new StringBuilder();
        if (!hasCustomApplicationName() && !hasUnparsedSpecification()) {
            message.append("The options available are:");
        } else {
            message.append("Usage: ");

            if (hasCustomApplicationName()) {
                message.append(String.format("%s ", applicationName()));
            }

            if (getMandatoryOptions().isEmpty()) {
                message.append("[");
            }
            message.append("options");
            if (getMandatoryOptions().isEmpty()) {
                message.append("]");
            }

            if (hasUnparsedSpecification()) {
                message.append(" ");
                final String unparsedName =
                        !nullOrBlank(getUnparsedSpecification().getLongName()) ? getUnparsedSpecification()
                                .getLongName() : "ARGUMENTS";
                message.append(unparsedName);

                if (getUnparsedSpecification().isMultiValued()) {
                    message.append("...");
                }
            }
        }

        final String lineSeparator = System.getProperty("line.separator");
        message.append(lineSeparator);

        String separator = "";
        for (final OptionSpecification specification : options) {
            message.append(separator).append("\t").append(specification);
            separator = lineSeparator;
        }

        return message.toString();
    }

    private boolean hasCustomApplicationName() {
        return !nullOrBlank(applicationName());
    }

    private boolean nullOrBlank(final String string) {
        return string == null || string.trim().equals("");
    }
}
