package com.lexicalscope.jewel.cli;

import java.util.List;


interface OptionCollection extends Iterable<Argument>
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

//    void forEach(ArgumentProcessor argumentProcessor);
}
