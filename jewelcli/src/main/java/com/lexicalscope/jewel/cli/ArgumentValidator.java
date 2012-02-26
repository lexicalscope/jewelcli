package com.lexicalscope.jewel.cli;

interface ArgumentValidator<O>
{
   ArgumentCollection validateArguments(ArgumentCollection arguments) throws ArgumentValidationException;
}