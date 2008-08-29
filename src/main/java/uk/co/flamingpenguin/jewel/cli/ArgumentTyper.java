package uk.co.flamingpenguin.jewel.cli;

interface ArgumentTyper<T>
{
   TypedArguments typeArguments(ArgumentCollection validatedArguments) throws ArgumentValidationException;
}