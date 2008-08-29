package uk.co.flamingpenguin.jewel.cli;

interface ArgumentValidator<O>
{
   ValidatedArguments validateArguments(ArgumentCollection arguments) throws ArgumentValidationException;
}