package com.lexicalscope.jewel.cli.parser;

import com.lexicalscope.jewel.cli.ArgumentValidationException;

public interface ArgumentParser
{
    void parseArguments(ParsedArguments parsedArguments, String... arguments) throws ArgumentValidationException;
}