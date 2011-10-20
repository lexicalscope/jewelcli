package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
            unparsed.add(value);
            return new NoOptions();
        }

        @Override public IParsingState addOption(final String option) {
            currentValues = new ArrayList<String>();
            arguments.put(option, currentValues);
            return new OptionOrValue();
        }
    }

    private class NoOptions implements IParsingState {
        @Override public IParsingState addValue(final String value) {
            unparsed.add(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) throws ArgumentValidationException {
            throw new ArgumentValidationException(new ArgumentValidationException.ValidationError() {
                public ErrorType getErrorType()
                {
                    return ArgumentValidationException.ValidationError.ErrorType.MisplacedOption;
                }

                public String getMessage()
                {
                    return option;
                }

                @Override public String toString()
                {
                    return String.format("Option not expected in this position: %s", getMessage());
                }
            });
        }
    }

    private class UnparsedState implements IParsingState {
        public UnparsedState() {
            currentValues = null;
        }

        @Override public IParsingState addValue(final String value) {
            unparsed.add(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) throws ArgumentValidationException {
            throw new ArgumentValidationException(new ArgumentValidationException.ValidationError() {
                public ErrorType getErrorType()
                {
                    return ArgumentValidationException.ValidationError.ErrorType.MisplacedOption;
                }

                public String getMessage()
                {
                    return option;
                }

                @Override public String toString()
                {
                    return String.format("Option not expected in this position: %s", getMessage());
                }
            });
        }
    }

    private class OptionOrValue implements IParsingState {
        @Override public IParsingState addValue(final String value) {
            currentValues.add(value);
            return this;
        }

        @Override public IParsingState addOption(final String option) {
            currentValues = new ArrayList<String>();
            arguments.put(option, currentValues);
            return new OptionOrValue();
        }
    }

    private final Map<String, List<String>> arguments = new LinkedHashMap<String, List<String>>();
    private final List<String> unparsed = new ArrayList<String>();

    private IParsingState state = new Initial();
    private List<String> currentValues;

    void unparsedOptionsFollow() {
        state = new UnparsedState();
    }

    boolean expectingUnparsedOptions() {
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
        final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

        for (final Entry<String, List<String>> entry : arguments.entrySet())
        {
            finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
        }

        return new ArgumentCollectionImpl(finalArguments, new ArrayList<String>(unparsed));
    }
}
