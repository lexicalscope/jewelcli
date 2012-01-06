package com.lexicalscope.jewel.cli;

import com.lexicalscope.jewel.JewelRuntimeException;

/**
 * The specification of the options is not valid
 * 
 * @author tim
 */
public class InvalidOptionSpecificationException extends JewelRuntimeException
{
    private static final long serialVersionUID = -5023726790561988859L;

    /**
     * A new exception with no message
     */
    public InvalidOptionSpecificationException()
    {
        super();
    }

    /**
     * A new exception with the given message and cause
     * 
     * @param message
     *            The message
     * @param cause
     *            The cause
     */
    public InvalidOptionSpecificationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * A new exception with the given message
     * 
     * @param message
     *            The message
     */
    public InvalidOptionSpecificationException(final String message)
    {
        super(message);
    }

    /**
     * A new exception with the given cause
     * 
     * @param cause
     *            The cause
     */
    public InvalidOptionSpecificationException(final Throwable cause)
    {
        super(cause);
    }
}
