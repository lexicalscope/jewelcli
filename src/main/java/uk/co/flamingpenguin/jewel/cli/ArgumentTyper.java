package uk.co.flamingpenguin.jewel.cli;

public interface ArgumentTyper<T>
{
   TypedArguments typeArguments(ValidatedArguments validatedArguments) throws ArgumentValidationException;
}