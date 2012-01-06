package com.lexicalscope.jewel.cli;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.lexicalscope.jewel.JewelRuntimeException;

/**
 * The arguments are not valid
 * 
 * @author tim
 */
public class CliValidationException extends JewelRuntimeException
{
    private static final long serialVersionUID = -4781861924515211053L;
    static final ResourceBundle m_messages = ResourceBundle.getBundle(
            "com.lexicalscope.jewel.cli.Messages",
            Locale.getDefault());

    private final List<ValidationFailure> validationErrors;

    public CliValidationException() {
        this(new ArrayList<ValidationFailure>());
    }

    public CliValidationException(final String message, final Throwable cause) {
        this(message, cause, new ArrayList<ValidationFailure>());
    }

    public CliValidationException(final String message) {
        this(message, new ArrayList<ValidationFailure>());
    }

    public CliValidationException(final Throwable cause) {
        this(cause, new ArrayList<ValidationFailure>());
    }

    public CliValidationException(final ValidationFailure validationError)
    {
        this(asList(validationError));
    }

    public CliValidationException(final List<ValidationFailure> validationErrors)
    {
        this(createMessageFromErrors(validationErrors), validationErrors);
    }

    public CliValidationException(
            final String message,
            final Throwable cause,
            final List<ValidationFailure> validationErrors) {
        super(message, cause);
        this.validationErrors = validationErrors;
    }

    public CliValidationException(final String message, final List<ValidationFailure> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public CliValidationException(final Throwable cause, final List<ValidationFailure> validationErrors) {
        this(createMessageFromErrors(validationErrors), cause, validationErrors);
    }

    public List<ValidationFailure> getValidationErrors()
    {
        return validationErrors;
    }

    private static String createMessageFromErrors(final List<ValidationFailure> validationErrors) {
        final StringBuilder message = new StringBuilder();

        String separator = "";
        for (final ValidationFailure error : validationErrors)
        {
            message.append(separator).append(error.getMessage());
            separator = System.getProperty("line.separator");
        }
        return message.toString();
    }
}
