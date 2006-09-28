package uk.co.flamingpenguin.jewel.cli;

import java.util.ArrayList;
import java.util.List;


class ValidationErrorBuilderImpl implements ValidationErrorBuilder
{
   private final List<ArgumentValidationException.ValidationError> m_validationException = new ArrayList<ArgumentValidationException.ValidationError>();

   public void missingValue(final OptionSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingValueError(optionSpecification));
   }

   public void unexpectedAdditionalValues(final OptionSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createAdditionalValuesError(optionSpecification));
   }

   public void unexpectedOption(final String name)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedOptionError(name));
   }

   public void unexpectedValue(final OptionSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createUnexpectedValueError(optionSpecification));
   }

   public void missingOption(final OptionSpecification optionSpecification)
   {
      m_validationException.add(ArgumentValidationException.createMissingOptionError(optionSpecification));
   }

   public void invalidValueForType(final OptionSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createInvalidValueForType(optionSpecification, message));
   }

   public void unableToConstructType(final OptionSpecification optionSpecification, final String message)
   {
      m_validationException.add(ArgumentValidationException.createUnableToConstructType(optionSpecification, message));
   }

   public void patternMismatch(final OptionSpecification optionSpecification, final String value)
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
