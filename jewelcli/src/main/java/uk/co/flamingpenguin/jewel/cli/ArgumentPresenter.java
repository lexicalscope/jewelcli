package uk.co.flamingpenguin.jewel.cli;

interface ArgumentPresenter<O>
{
   O presentArguments(TypedArguments arguments);
}
