package com.lexicalscope.jewel.cli.examples;

import java.util.List;

import com.lexicalscope.jewel.cli.Option;

public class ClassOptionalUnparsedOption
{
    private List<Integer> myOptionalUnparsedValues;

    List<Integer> getMyOptionalUnparsedOption()
    {
        return myOptionalUnparsedValues;
    }

    @Option(defaultValue = {}) void setMyOptionalUnparsedOption(final List<Integer> myOptionalUnparsedValues) {
        this.myOptionalUnparsedValues = myOptionalUnparsedValues;
    }
}
