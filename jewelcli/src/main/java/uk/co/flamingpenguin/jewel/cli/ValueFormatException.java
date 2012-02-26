package uk.co.flamingpenguin.jewel.cli;

import uk.co.flamingpenguin.jewel.JewelException;

/**
 * Thrown when a value has an invalid format
 * @deprecated please use {@link com.lexicalscope.jewel.cli.ArgumentValidationException} instead
 * @author Tim Wood
 */
@Deprecated class ValueFormatException extends JewelException
{
    private static final long serialVersionUID = -5278572593150819070L;

    /**
     * A value had an invalid format
     *
     * @param message A message describing the problem
     */
    ValueFormatException(final String message)
    {
        super(message);
    }
}
