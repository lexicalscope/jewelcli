package uk.co.flamingpenguin.jewel.cli;

import uk.co.flamingpenguin.jewel.JewelRuntimeException;

/**
 * The value of an option has been requested, but is not available
 * 
 * @deprecated please use {@link com.lexicalscope.jewel.cli.ArgumentValidationException} instead
 * 
 * @author tim
 */
@Deprecated public class OptionNotPresentException extends JewelRuntimeException
{
    private static final long serialVersionUID = -5023726790561988859L;

    /**
     * A new exception with no message
     */
    public OptionNotPresentException()
    {
        super();
    }

    /**
     * A new exception with the given message and cause
     *
     * @param message The message
     * @param cause The cause
     */
    public OptionNotPresentException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * A new exception with the given message
     *
     * @param message The message
     */
    public OptionNotPresentException(final String message)
    {
        super(message);
    }

    /**
     * A new exception with the given cause
     *
     * @param cause The cause
     */
    public OptionNotPresentException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * An option has been requested that is not available
     *
     * @param specification The option which has been requested
     */
    public OptionNotPresentException(final OptionSpecification specification)
    {
        this(String.format("Unable to find value for option: %s", specification));
    }
}
