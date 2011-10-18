package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

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

    /**
     * Get the values associated with the given options
     * 
     * @param options
     *            the options to obtain values for
     * 
     * @return the values associated with the given options
     */
    List<String> getValues(List<String> options);

    Argument getArgument(List<String> options);
}
