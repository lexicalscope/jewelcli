package com.lexicalscope.jewel.cli;

interface ArgumentParser
{
    ArgumentCollection parseArguments(String... arguments) throws CliValidationException;
}