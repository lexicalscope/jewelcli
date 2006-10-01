package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.List;


class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
   private final List<ArgumentValidationException.ValidationError> m_validationException = new ArrayList<ArgumentValidationException.ValidationError>();

   public void missingValue(final ArgumentSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingValueError(optionSpecification));
   }

   public void unexpectedAdditionalValues(final ArgumentSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createAdditionalValuesError(optionSpecification));
   }

   public void unexpectedOption(final String name)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedOptionError(name));
   }

   public void unexpectedValue(final ArgumentSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedValueError(optionSpecification));
   }

   public void missingOption(final ArgumentSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingOptionError(optionSpecification));
   }

   public void invalidValueForType(final ArgumentSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createInvalidValueForType(optionSpecification, message));
   }

   public void unableToConstructType(final ArgumentSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createUnableToConstructType(optionSpecification, message));
   }

   public void patternMismatch(final ArgumentSpecification optionSpecification, final String value)
   {
      m_validationException.add(ArgumentValidationException.createPatternMismatch(optionSpecification, value));
   }

   public void validate() throws ArgumentValidationException
   {
      if(m_validationException.size() > 0)
      {
         throw new ArgumentValidationException(m_validationException);
      }
   }
}
