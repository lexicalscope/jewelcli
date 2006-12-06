package uk.co.flamingpenguin.jewel.cli;

interface ArgumentParser
{
   ParsedArguments parseArguments() throws ArgumentValidationException;
}