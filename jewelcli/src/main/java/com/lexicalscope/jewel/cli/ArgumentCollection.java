package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.jewel.cli.arguments.ArgumentProcessor;


interface ArgumentCollection extends Iterable<Argument>
{
    /**
     * Get any unparsed arguments
     *
     * @return the unparsed arguments
     */
    List<String> getUnparsed();

    /**
     * Does the collection contain any of the given options
     *
     * @param options
     *            the options
     *
     * @return true iff the collection contain any of the given options
     */
    boolean containsAny(List<String> options);

    Argument getArgument(List<String> options);

    void forEach(ArgumentProcessor argumentProcessor);
}
