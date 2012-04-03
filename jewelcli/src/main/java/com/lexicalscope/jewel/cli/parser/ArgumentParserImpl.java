package com.lexicalscope.jewel.cli.parser;

import com.lexicalscope.jewel.cli.ArgumentValidationException;

class ArgumentParserImpl implements ArgumentParser
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void parseArguments(final ParsedArguments parsedArguments, final String... arguments) throws ArgumentValidationException
    {
        boolean finishedOptions = false;
        for (final String argument : arguments)
        {
           if(finishedOptions)
           {
               parsedArguments.addValue(argument);
           }
           else
           {
               finishedOptions = add(parsedArguments, argument);
           }
        }
    }

    private boolean add(final ParsedArguments parsedArguments, final String argument) throws ArgumentValidationException
    {
        if (startsWithDash(argument))
        {
            if (startsWithDoubleDash(argument))
            {
                if (argument.length() > 2)
                {
                    addOptionAndValue(parsedArguments, argument);
                }
                else
                {
                    parsedArguments.unparsedOptionsFollow();
                    return true;
                }
            }
            else
            {
                addConjoinedOptions(parsedArguments, argument.substring(1));
            }
        }
        else
        {
            parsedArguments.addValue(argument);
        }
       return false;
    }

    private void addConjoinedOptions(final ParsedArguments parsedArguments, final String options) throws ArgumentValidationException
    {
        for (int i = 0; i < options.length(); i++)
        {
            parsedArguments.addOption(options.substring(i, i + 1));
        }
    }

    private void addOptionAndValue(final ParsedArguments parsedArguments, final String argument) throws ArgumentValidationException
    {
        if (argument.contains("="))
        {
            final int separatorIndex = argument.indexOf("=");
            parsedArguments.addOption(argument.substring(2, separatorIndex).trim());

            if (argument.length() > separatorIndex + 1)
            {
                parsedArguments.addValue(argument.substring(separatorIndex + 1).trim());
            }
        }
        else
        {
            parsedArguments.addOption(argument.substring(2, argument.length()).trim());
        }
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
