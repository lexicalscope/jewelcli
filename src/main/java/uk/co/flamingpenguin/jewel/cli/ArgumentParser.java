package uk.co.flamingpenguin.jewel.cli;

interface ArgumentParser
{
   ArgumentCollection parseArguments() throws ArgumentValidationException;
}