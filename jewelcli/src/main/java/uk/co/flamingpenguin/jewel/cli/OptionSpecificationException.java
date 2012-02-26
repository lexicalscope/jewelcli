package uk.co.flamingpenguin.jewel.cli;

import uk.co.flamingpenguin.jewel.JewelRuntimeException;

/**
 * The specification of the options is not valid
 * 
 * @deprecated please use {@link com.lexicalscope.jewel.cli.InvalidOptionSpecificationException} instead
 * 
 * @author tim
 */
@Deprecated public class OptionSpecificationException extends JewelRuntimeException
{
    private static final long serialVersionUID = -5023726790561988859L;

    /**
     * A new exception with no message
     */
    public OptionSpecificationException()
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
    public OptionSpecificationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * A new exception with the given message
     * 
     * @param message
     *            The message
     */
    public OptionSpecificationException(final String message)
    {
        super(message);
    }

    /**
     * A new exception with the given cause
     * 
     * @param cause
     *            The cause
     */
    public OptionSpecificationException(final Throwable cause)
    {
        super(cause);
    }
}
