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

package com.lexicalscope.jewel.cli;

import com.lexicalscope.fluent.FluentDollar;
import com.lexicalscope.fluent.list.FluentList;
import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.jewel.cli.specification.CliSpecification;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;
import com.lexicalscope.jewel.cli.specification.UnparsedOptionSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class OptionsSpecificationImpl<O> implements OptionsSpecification<O>, CliSpecification {
    private final ReflectedClass<O> klass;

    private final Set<ParsedOptionSpecification> options;
    private final Map<String, ParsedOptionSpecification> optionsByName =
            new TreeMap<String, ParsedOptionSpecification>();
    private final Map<ReflectedMethod, ParsedOptionSpecification> optionsMethod =
            new HashMap<ReflectedMethod, ParsedOptionSpecification>();
    private final Map<ReflectedMethod, ParsedOptionSpecification> optionalOptionsMethod =
            new HashMap<ReflectedMethod, ParsedOptionSpecification>();

    private final Map<ReflectedMethod, UnparsedOptionSpecification> unparsedOptionsMethod =
            new HashMap<ReflectedMethod, UnparsedOptionSpecification>();
    private final Map<ReflectedMethod, UnparsedOptionSpecification> unparsedOptionalOptionsMethod =
            new HashMap<ReflectedMethod, UnparsedOptionSpecification>();

    OptionsSpecificationImpl(
            final ReflectedClass<O> klass,
            final List<ParsedOptionSpecification> optionSpecifications,
            final List<UnparsedOptionSpecification> unparsedSpecifications) {
        this.klass = klass;

        if(klass.annotatedWith(CommandLineInterface.class) &&
           klass.annotation(CommandLineInterface.class).order().equals(OptionOrder.DEFINITION))
        {
            options = new LinkedHashSet<ParsedOptionSpecification>();
        }
        else
        {
            options = new TreeSet<ParsedOptionSpecification>();
        }

        for (final ParsedOptionSpecification optionSpecification : optionSpecifications) {
            addOption(optionSpecification);
        }

        for (final UnparsedOptionSpecification optionSpecification : unparsedSpecifications) {
            addUnparsedOption(optionSpecification);
        }
    }

    @Override public boolean isSpecified(final String key) {
        return optionsByName.containsKey(key);
    }

    @Override public ParsedOptionSpecification getSpecification(final String key) {
        return optionsByName.get(key);
    }

    @Override public ParsedOptionSpecification getSpecification(final ReflectedMethod method) {
        if (optionsMethod.containsKey(method)) {
            return optionsMethod.get(method);
        }
        return optionalOptionsMethod.get(method);
    }

    @Override public FluentList<ParsedOptionSpecification> getMandatoryOptions() {
        final FluentList<ParsedOptionSpecification> result = FluentDollar.$.list(ParsedOptionSpecification.class);
        for (final ParsedOptionSpecification specification : options) {
            if (!specification.isOptional() && !specification.hasDefaultValue()) {
                result.add(specification);
            }
        }

        return result;
    }

    @Override public Iterator<ParsedOptionSpecification> iterator() {
        return new ArrayList<ParsedOptionSpecification>(optionsByName.values()).iterator();
    }

    @Override public UnparsedOptionSpecification getUnparsedSpecification() {
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

    private void addOption(final ParsedOptionSpecification optionSpecification) {
        for (final String name : optionSpecification.getNames()) {
            optionsByName.put(name, optionSpecification);
        }
        options.add(optionSpecification);

        optionsMethod.put(optionSpecification.getMethod(), optionSpecification);

        if (optionSpecification.isOptional()) {
            optionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
        }
    }

    private void addUnparsedOption(final UnparsedOptionSpecification optionSpecification) {
        unparsedOptionsMethod.put(optionSpecification.getMethod(), optionSpecification);

        if (optionSpecification.isOptional()) {
            unparsedOptionalOptionsMethod.put(optionSpecification.getOptionalityMethod(), optionSpecification);
        }
    }

    @Override public void describeTo(final HelpMessage helpMessage) {
        if (!hasCustomApplicationName() && (!hasUnparsedSpecification() || getUnparsedSpecification().isHidden())) {
            helpMessage.noUsageInformation();
        } else {
            if (hasCustomApplicationName()) {
                helpMessage.hasUsageInformation(applicationName());
            }
            else
            {
                helpMessage.hasUsageInformation();
            }

            if (getMandatoryOptions().isEmpty()) {
                helpMessage.hasOnlyOptionalOptions();
            }
            else
            {
                helpMessage.hasSomeMandatoryOptions();
            }

            if (hasUnparsedSpecification() && !getUnparsedSpecification().isHidden()) {
                if (getUnparsedSpecification().isMultiValued()) {
                    helpMessage.hasUnparsedMultiValuedOption(getUnparsedSpecification().getValueName());
                }
                else
                {
                    helpMessage.hasUnparsedOption(getUnparsedSpecification().getValueName());
                }
            }
        }

        helpMessage.startOfOptions();

        for (final ParsedOptionSpecification specification : options) {
            if(!specification.isHidden())
            {
                new ParsedOptionSummary(specification).describeOptionTo(helpMessage.option());
            }
        }

        helpMessage.endOfOptions();
    }

    private boolean hasCustomApplicationName() {
        return !nullOrBlank(applicationName());
    }

    static boolean nullOrBlank(final String string) {
        return string == null || string.trim().equals("");
    }

    @Override public String toString() {
        final HelpMessageBuilderImpl helpMessage = new HelpMessageBuilderImpl();
        describeTo(helpMessage);
        return helpMessage.toString();
    }
}
