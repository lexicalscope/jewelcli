package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.flamingpenguin.jewel.JewelException;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

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
         MisplacedValue
         {
            public String getDescription(final ValidationError error)
            {
               return String.format("Value mispaced (%s)", error.getMessage());
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
               return "Option is manditory";
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
         };

         public abstract String getDescription(ValidationError error);
      }

      OptionSpecification getSpecification();
      String getMessage();
      ErrorType getErrorType();
   }

   private static class ValidationErrorImpl implements ValidationError
   {
      private final ErrorType m_errorType;
      private final OptionSpecification m_specification;
      private final String m_message;

      public ValidationErrorImpl(final ErrorType errorType, final OptionSpecification specification)
      {
         this(errorType, specification, "");
      }

      public ValidationErrorImpl(final ErrorType errorType, final OptionSpecification specification, final String message)
      {
         m_errorType = errorType;
         m_specification = specification;
         m_message = message;
      }

      public ErrorType getErrorType()
      {
         return m_errorType;
      }

      public OptionSpecification getSpecification()
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

   public static ValidationError createUnexpectedOptionError(final String name)
   {
      return new ValidationErrorImpl(ErrorType.UnexpectedOption, new UnexpectedOptionSpecification(name));
   }

   public static ValidationError createAdditionalValuesError(final OptionSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.AdditionalValue, optionSpecification);
   }

   public static ValidationError createMissingValueError(final OptionSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.MissingValue, optionSpecification);
   }

   public static ValidationError createUnexpectedValueError(final OptionSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.UnexpectedValue, optionSpecification);
   }

   public static ValidationError createMissingOptionError(OptionSpecification optionSpecification)
   {
      return new ValidationErrorImpl(ErrorType.MissingOption, optionSpecification);
   }

   public static ValidationError createInvalidValueForType(final OptionSpecification optionSpecification, final String message)
   {
      return new ValidationErrorImpl(ErrorType.InvalidValueForType, optionSpecification, message);
   }

   public static ValidationError createUnableToCOnstructType(OptionSpecification optionSpecification, String message)
   {
      return new ValidationErrorImpl(ErrorType.UnableToConstructType, optionSpecification, message);
   }
}
