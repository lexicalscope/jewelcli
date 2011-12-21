package com.lexicalscope.jewel.cli;

interface ArgumentPresenter<O> {
    O presentArguments(ArgumentCollection validatedArguments) throws CliValidationException;
}
