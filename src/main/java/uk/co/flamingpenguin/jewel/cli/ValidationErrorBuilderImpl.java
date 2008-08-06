package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;


class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
   private final List<ArgumentValidationException.ValidationError> m_validationException = new ArrayList<ArgumentValidationException.ValidationError>();

   public void missingValue(final ArgumentMethodSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingValueError(optionSpecification));
   }

   public void unexpectedAdditionalValues(final ArgumentMethodSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createAdditionalValuesError(optionSpecification));
   }

   public void unexpectedOption(final String name)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedOptionError(name));
   }

   public void unexpectedValue(final ArgumentMethodSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedValueError(optionSpecification));
   }

   public void missingOption(final ArgumentMethodSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingOptionError(optionSpecification));
   }

   public void invalidValueForType(final ArgumentMethodSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createInvalidValueForType(optionSpecification, message));
   }

   public void unableToConstructType(final ArgumentMethodSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createUnableToConstructType(optionSpecification, message));
   }

   public void patternMismatch(final ArgumentMethodSpecification optionSpecification, final String value)
   {
      m_validationException.add(ArgumentValidationException.createPatternMismatch(optionSpecification, value));
   }

   public void helpRequested(final OptionsSpecification<?> specification)
   {
      m_validationException.add(ArgumentValidationException.createhelpRequested(specification));
   }

   public void validate() throws ArgumentValidationException
   {
      if(m_validationException.size() > 0)
      {
         for (ArgumentValidationException.ValidationError error : m_validationException)
         {
            if(ErrorType.HelpRequested.equals(error.getErrorType()))
            {
               throw new ArgumentValidationException(Arrays.asList(error));
            }
         }

         throw new ArgumentValidationException(m_validationException);
      }
   }
}
