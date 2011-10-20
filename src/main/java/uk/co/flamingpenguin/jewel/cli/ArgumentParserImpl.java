package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ArgumentParserImpl implements ArgumentParser
{
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
            m_arguments.put(option, currentValues);
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
            m_arguments.put(option, currentValues);
            return new OptionOrValue();
        }
    }

    private final Map<String, List<String>> m_arguments = new LinkedHashMap<String, List<String>>();
    private final List<String> unparsed = new ArrayList<String>();

    private IParsingState state = new Initial();
    private List<String> currentValues;

    /**
     * {@inheritDoc}
     */
    public ArgumentCollection parseArguments(final String... arguments) throws ArgumentValidationException
    {
        for (final String argument : arguments)
        {
            add(argument);
        }
        return getParsedArguments();
    }

    /**
     * Add an argument to the set
     * 
     * @param argument
     *            the argument to parse
     * 
     * @throws ArgumentValidationException
     */
    void add(final String argument) throws ArgumentValidationException
    {
        if (startsWithDash(argument) && !(state instanceof UnparsedState))
        {
            if (startsWithDoubleDash(argument))
            {
                if (argument.length() > 2)
                {
                    addOptionAndValue(argument);
                }
                else
                {
                    state = new UnparsedState();
                }
            }
            else
            {
                addConjoinedOptions(argument.substring(1));
            }
        }
        else
        {
            addValue(argument);
        }
    }

    private void addConjoinedOptions(final String options) throws ArgumentValidationException
    {
        for (int i = 0; i < options.length(); i++)
        {
            addOption(options.substring(i, i + 1));
        }
    }

    private void addOptionAndValue(final String argument) throws ArgumentValidationException
    {
        if (argument.contains("="))
        {
            final int separatorIndex = argument.indexOf("=");
            addOption(argument.substring(2, separatorIndex).trim());

            if (argument.length() > separatorIndex + 1)
            {
                addValue(argument.substring(separatorIndex + 1).trim());
            }
        }
        else
        {
            addOption(argument.substring(2, argument.length()).trim());
        }
    }

    private void addValue(final String value)
    {
        state = state.addValue(value);
    }

    private void addOption(final String option) throws ArgumentValidationException
    {
        state = state.addOption(option);
    }

    /**
     * Obtain the parsed arguments
     * 
     * @return the arguments that have been parsed
     */
    ArgumentCollection getParsedArguments()
    {
        final Map<String, List<String>> finalArguments = new LinkedHashMap<String, List<String>>();

        for (final Entry<String, List<String>> entry : m_arguments.entrySet())
        {
            finalArguments.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
        }

        return new ArgumentCollectionImpl(finalArguments, new ArrayList<String>(unparsed));
    }

    private boolean startsWithDash(final String argument)
    {
        return argument.length() > 1 && argument.startsWith("-");
    }

    private boolean startsWithDoubleDash(final String argument)
    {
        return argument.startsWith("--");
    }
}
