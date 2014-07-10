package com.lexicalscope.jewel.cli;

import com.google.common.base.Throwables;
import com.lexicalscope.fluentreflection.InvocationTargetRuntimeException;

/**
 * Constructs a Cli from an annotated interface definition.
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
     *
     * @throws ArgumentValidationException the arguments do not meet the CLI specification
     * @throws InvalidOptionSpecificationException the CLI specification is not valid
     */
    public static <O> Cli<O> createCli(final Class<O> klass) throws InvalidOptionSpecificationException
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
     *
     * @throws ArgumentValidationException the arguments do not meet the CLI specification
     * @throws InvalidOptionSpecificationException the CLI specification is not valid
     */
    public static <O> Cli<O> createCliUsingInstance(final O options) throws InvalidOptionSpecificationException
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
     * @throws ArgumentValidationException the arguments do not meet the CLI specification
     * @throws InvalidOptionSpecificationException the CLI specification is not valid
     */
    public static <O> O parseArguments(final Class<O> klass, final String... arguments)
            throws ArgumentValidationException, InvalidOptionSpecificationException
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
     * @throws ArgumentValidationException the arguments do not meet the CLI specification
     * @throws InvalidOptionSpecificationException the CLI specification is not valid
     * @throws RuntimeException a setter method has thrown an exception 
     */
    public static <O> O parseArgumentsUsingInstance(final O options, final String... arguments)
            throws ArgumentValidationException, InvalidOptionSpecificationException, RuntimeException
    {
    	try {
    		return createCliUsingInstance(options).parseArguments(arguments);
    	} catch (InvocationTargetRuntimeException e) {
    		Throwable rootCause = Throwables.getRootCause(e); 
    		if (rootCause instanceof RuntimeException) {
    			throw (RuntimeException) e;
    		} else {
				throw new InvalidOptionSpecificationException(
						"Throwing checked exceptions in setter methods is not allowed");
    		}
    	}
    }
}
