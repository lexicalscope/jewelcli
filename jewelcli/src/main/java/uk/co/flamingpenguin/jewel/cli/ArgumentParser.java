package uk.co.flamingpenguin.jewel.cli;

interface ArgumentParser
{
    ArgumentCollection parseArguments(String... arguments) throws ArgumentValidationException;
}