package uk.co.flamingpenguin.jewel.cli;

/**
 * Parses arguments and presents them, in a typesafe style, as an instance of
 * the interface <code>O</code>
 * 
 * @author Tim Wood
 * 
 * @deprecated please use {@link com.lexicalscope.jewel.cli.Cli} instead
 * 
 * @param <O>
 *            The type of interface provided by this Cli
 */
@Deprecated
public interface Cli<O> {
    /**
     * Parse the arguments and present them as an instance of the interface O
     * 
     * @param arguments
     *            The arguments that will be parsed
     * 
     * @return An instance of the interface O which will present the parsed
     *         arguments
     * 
     * @throws InvalidArgumentsException
     * @throws ArgumentValidationException
     */
    O parseArguments(String... arguments) throws ArgumentValidationException;

    /**
     * Get a help message suitable for describing the options to the user
     * 
     * @return A help message
     */
    String getHelpMessage();

    /**
     * BETA: may be removed or altered in future versions
     * 
     * Fill in a help message suitable for describing the options to the user
     */
    void describeTo(HelpMessage helpMessage);
}