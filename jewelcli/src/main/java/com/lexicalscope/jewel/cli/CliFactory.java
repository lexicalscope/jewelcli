package com.lexicalscope.jewel.cli;

/**
 * <p>
 * Constructs a Cli from an annotated interface definition.
 * </p>
 * 
 * @see com.lexicalscope.jewel.cli.Option
 * 
 * @author Tim Wood
 */
public abstract class CliFactory
{
    /**
     * Construct a Cli from an annotated interface definition
     * 
     * @param <O>
     *            The type of the interface that will be used to present the
     *            arguments
     * @param klass
     *            The annotated interface definition
     * 
     * @return A Cli configured to create instance of klass
     */
    public static <O> Cli<O> createCli(final Class<O> klass)
    {
        return new CliInterfaceImpl<O>(klass);
    }

    /**
     * Construct a Cli from an annotated class
     * 
     * @param <O>
     *            The type of the class used to present the arguments
     * @param options
     *            The annotated class
     * 
     * @return A Cli configured to configure the options
     */
    public static <O> Cli<O> createCliUsingInstance(final O options)
    {
        return new CliInstanceImpl<O>(options);
    }

    /**
     * Parse arguments from an annotated interface definition
     * 
     * @param <O>
     *            The type of the interface that will be used to present the
     *            arguments
     * @param klass
     *            The annotated interface definition
     * @param arguments
     * 
     * @return The parsed arguments
     * 
     * @throws InvalidArgumentsException
     * @throws ArgumentValidationException
     */
    public static <O> O parseArguments(final Class<O> klass, final String... arguments)
            throws ArgumentValidationException
    {
        return createCli(klass).parseArguments(arguments);
    }

    /**
     * Parse arguments from an annotated class instance
     * 
     * @param <O>
     *            The type of the class used to present the arguments
     * @param klass
     *            The annotated interface definition
     * @param arguments
     * 
     * @return The parsed arguments
     * 
     * @throws InvalidArgumentsException
     * @throws ArgumentValidationException
     */
    public static <O> O parseArgumentsUsingInstance(final O options, final String... arguments)
            throws ArgumentValidationException
    {
        return createCliUsingInstance(options).parseArguments(arguments);
    }
}
