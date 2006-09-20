package uk.co.flamingpenguin.jewel.cli;

interface ArgumentTyper<T>
{
   TypedArguments typeArguments(ValidatedArguments validatedArguments) throws ArgumentValidationException;
}