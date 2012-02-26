package com.lexicalscope.jewel.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

class ArgumentCollectionBuilder {
    private interface IParsingState {
        IParsingState addValue(String value);

        IParsingState addOption(String option) throws ArgumentValidationException;
    }

    private class Initial implements IParsingState {
        @Override public IParsingState addValue(final String value) {
            return new NoOptions(value);
        }

        @Override public IParsingState addOption(final String option) {
            return new OptionOrValue(option);
        }
    }

    private class NoOptions implements IParsingState {
        public NoOptions(final String value) {
            addUnparsedValue(value);
        }

        @Override public IParsingState addValue(final String value) {
            addUnparsedValue(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) throws ArgumentValidationException {
            throw misplacedOption(option);
        }
    }

    private class UnparsedState implements IParsingState {
        public UnparsedState() {
            valuesForCurrentArgument = null;
        }

        @Override public IParsingState addValue(final String value) {
            addUnparsedValue(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) throws ArgumentValidationException {
            throw misplacedOption(option);
        }
    }

    private class OptionOrValue implements IParsingState {
        public OptionOrValue(final String option) {
            addFirstValueForOption(option);
        }

        @Override public IParsingState addValue(final String value) {
            valuesForCurrentArgument.add(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) {
            return new OptionOrValue(option);
        }
    }

    private final Map<String, List<String>> arguments = new LinkedHashMap<String, List<String>>();
    private final List<String> unparsed = new ArrayList<String>();

    private IParsingState state = new Initial();
    private List<String> valuesForCurrentArgument;

    void unparsedOptionsFollow() {
        state = new UnparsedState();
    }

    boolean isExpectingUnparsedOptions() {
        return state.getClass().equals(UnparsedState.class);
    }

    void addValue(final String value)
    {
        state = state.addValue(value);
    }

    void addOption(final String option) throws ArgumentValidationException
    {
        state = state.addOption(option);
    }

    ArgumentCollection getParsedArguments()
    {
        return new ArgumentCollectionImpl(
                new LinkedHashMap<String, List<String>>(arguments),
                new ArrayList<String>(unparsed));
    }

    private ArgumentValidationException misplacedOption(final String option) {
        return new ArgumentValidationException(new ValidationFailureMisplacedOption(option));
    }

    private void addUnparsedValue(final String value) {
        unparsed.add(value);
    }

    private void addFirstValueForOption(final String option) {
        valuesForCurrentArgument = new ArrayList<String>();
        arguments.put(option, valuesForCurrentArgument);
    }
}
