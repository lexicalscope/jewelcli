package uk.co.flamingpenguin.jewel.cli;

interface ArgumentValidator<O>
{
   ValidatedArguments validateArguments(ParsedArguments arguments) throws ArgumentValidationException;
}