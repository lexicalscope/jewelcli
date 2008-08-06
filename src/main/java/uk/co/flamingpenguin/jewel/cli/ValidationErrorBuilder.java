package uk.co.flamingpenguin.jewel.cli;

interface ValidationErrorBuilder
{
   void unexpectedOption(String name);

   void unexpectedValue(ArgumentMethodSpecification optionSpecification);

   void missingValue(ArgumentMethodSpecification optionSpecification);

   void unexpectedAdditionalValues(ArgumentMethodSpecification optionSpecification);

   void missingOption(ArgumentMethodSpecification optionSpecification);

   void unableToConstructType(ArgumentMethodSpecification optionSpecification, String message);

   void invalidValueForType(ArgumentMethodSpecification optionSpecification, String message);

   void patternMismatch(ArgumentMethodSpecification optionSpecification, String value);

   void helpRequested(OptionsSpecification<?> specification);

   void validate() throws ArgumentValidationException;
}
