package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.flamingpenguin.jewel.JewelException;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

/**
 * The arguments are not valid
 *
 * @author tim
 */
public class ArgumentValidationException extends JewelException
{
   private static final long serialVersionUID = -4781861924515211053L;

   public interface ValidationError
   {
      enum ErrorType
      {
         UnexpectedOption
         {
            public String getDescription(final ValidationError error)
            {
               return "Unexpected Option";
            }
         },
         MissingValue
         {
            public String getDescription(final ValidationError error)
            {
               return "Option must have a value";
            }
         },
         MisplacedOption
         {
            public String getDescription(final ValidationError error)
            {
               return String.format("Option misplaced (%s)", error.getMessage());
            }
         },
         UnexpectedValue
         {
            public String getDescription(final ValidationError error)
            {
               return "Option does not take a value";
            }
         },
         AdditionalValue
         {
            public String getDescription(final ValidationError error)
            {
               return "Option only takes one value";
            }
         },
         MissingOption
         {
            public String getDescription(final ValidationError error)
            {
               return "Option is mandatory";
            }
         },
         InvalidValueForType
         {
            public String getDescription(final ValidationError error)
            {
               return String.format("Invalid value (%s)", error.getMessage());
            }
         },
         UnableToConstructType
         {
            public String getDescription(final ValidationError error)
            {
               return String.format("Unexpected error (%s)", error.getMessage());
            }
         },
         PatternMismatch
         {
            public String getDescription(final ValidationError error)
            {
               return String.format("Cannot match (%s) to pattern ", error.getMessage());
            }
         }
         ;

         public abstract String getDescription(ValidationError error);
      }

      ArgumentSpecification getSpecification();
      String getMessage();
      ErrorType getErrorType();
   }

   private static class ValidationErrorImpl implements ValidationError
   {
      private final ErrorType m_errorType;
      private final ArgumentSpecification m_specification;
      private final String m_message;

      public ValidationErrorImpl(final ErrorType errorType, final ArgumentSpecification specification)
      {
         this(errorType, specification, "");
      }

      public ValidationErrorImpl(final ErrorType errorType, final ArgumentSpecification specification, final String message)
      {
         m_errorType = errorType;
         m_specification = specification;
         m_message = message;
      }

      public ErrorType getErrorType()
      {
         return m_errorType;
      }

      public ArgumentSpecification getSpecification()
      {
         return m_specification;
      }

      @Override
      public String toString()
      {
         return String.format("%s: %s", getErrorType().getDescription(ValidationErrorImpl.this), getSpecification());
      }

      public String getMessage()
      {
         return m_message;
      }
   }

   private final ArrayList<ValidationError> m_validationErrors;
   private final String m_message;

   public ArgumentValidationException(final ValidationError validationError)
   {
      this(Arrays.asList(validationError));
   }

   public ArgumentValidationException(final List<ValidationError> validationErrors)
   {
      m_validationErrors = new ArrayList<ValidationError>(validationErrors);

      final StringBuilder message = new StringBuilder();

      String separator = "";
      for (final ValidationError error : validationErrors)
      {
         message.append(separator).append(error.toString());
         separator = System.getProperty("line.separator");
      }
      m_message = message.toString();
   }

   public ArrayList<ValidationError> getValidationErrors()
   {
      return m_validationErrors;
   }

   @Override
   public String getMessage()
   {
      return m_message;
   }

   static ValidationError createUnexpectedOptionError(final String name)
   {
      return new ValidationErrorImpl(ErrorType.UnexpectedOption, new UnexpectedOptionSpecification(name));
   }

   static ValidationError createAdditionalValuesError(final ArgumentSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.AdditionalValue, optionSpecification);
   }

   static ValidationError createMissingValueError(final ArgumentSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.MissingValue, optionSpecification);
   }

   static ValidationError createUnexpectedValueError(final ArgumentSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.UnexpectedValue, optionSpecification);
   }

   static ValidationError createMissingOptionError(ArgumentSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.MissingOption, optionSpecification);
   }

   static ValidationError createInvalidValueForType(final ArgumentSpecification optionSpecification, final String message)
   {
      return new ValidationErrorImpl(ErrorType.InvalidValueForType, optionSpecification, message);
   }

   static ValidationError createUnableToConstructType(final ArgumentSpecification optionSpecification, final String message)
   {
      return new ValidationErrorImpl(ErrorType.UnableToConstructType, optionSpecification, message);
   }

   static ValidationError createPatternMismatch(final ArgumentSpecification optionSpecification, final String message)
   {
      return new ValidationErrorImpl(ErrorType.PatternMismatch, optionSpecification, message);
   }
}
