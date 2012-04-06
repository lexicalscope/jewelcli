package com.lexicalscope.jewel.cli.validation;

import java.util.List;

import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;


public interface OptionCollection
{
    /**
     * Get any unparsed arguments
     *
     * @return the unparsed arguments
     */
    List<String> getUnparsed();

    Argument getArgument(ParsedOptionSpecification option);
}
