package com.lexicalscope.jewel.cli;

class ArgumentParserImpl implements ArgumentParser
{
    private final ArgumentCollectionBuilder builder = new ArgumentCollectionBuilder();

    /**
     * {@inheritDoc}
     */
    public ArgumentCollection parseArguments(final String... arguments) throws CliValidationException
    {
        for (final String argument : arguments)
        {
            add(argument);
        }
        return getParsedArguments();
    }

    private void add(final String argument) throws CliValidationException
    {
        if (startsWithDash(argument) && !builder.isExpectingUnparsedOptions())
        {
            if (startsWithDoubleDash(argument))
            {
                if (argument.length() > 2)
                {
                    addOptionAndValue(argument);
                }
                else
                {
                    builder.unparsedOptionsFollow();
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

    private void addConjoinedOptions(final String options) throws CliValidationException
    {
        for (int i = 0; i < options.length(); i++)
        {
            addOption(options.substring(i, i + 1));
        }
    }

    private void addOptionAndValue(final String argument) throws CliValidationException
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
        builder.addValue(value);
    }

    private void addOption(final String option) throws CliValidationException
    {
        builder.addOption(option);
    }

    ArgumentCollection getParsedArguments()
    {
        return builder.getParsedArguments();
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
