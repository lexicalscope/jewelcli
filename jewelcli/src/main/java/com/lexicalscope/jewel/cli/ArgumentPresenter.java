package com.lexicalscope.jewel.cli;


interface ArgumentPresenter<O> {
    O presentArguments(OptionCollection validatedArguments) throws ArgumentValidationException;
}
