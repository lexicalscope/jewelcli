package uk.co.flamingpenguin.jewel.cli;

interface ArgumentValidator<O>
{
   ArgumentCollection validateArguments(ArgumentCollection arguments) throws ArgumentValidationException;
}