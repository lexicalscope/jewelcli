package com.lexicalscope.jewel.cli;

import com.lexicalscope.jewel.cli.validation.OptionCollection;


interface ArgumentPresenter<O> {
    O presentArguments(OptionCollection validatedArguments) throws ArgumentValidationException;
}
