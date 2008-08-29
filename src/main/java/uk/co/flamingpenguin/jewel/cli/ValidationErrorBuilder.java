package uk.co.flamingpenguin.jewel.cli;

interface ValidationErrorBuilder
{
   void unexpectedOption(String name);

   void unexpectedValue(OptionArgumentsSpecification optionSpecification);

   void missingValue(OptionArgumentsSpecification optionSpecification);

   void unexpectedAdditionalValues(OptionArgumentsSpecification optionSpecification);

   void missingOption(OptionArgumentsSpecification optionSpecification);

   void unableToConstructType(OptionArgumentsSpecification optionSpecification, String message);

   void invalidValueForType(OptionArgumentsSpecification optionSpecification, String message);

   void patternMismatch(OptionArgumentsSpecification optionSpecification, String value);

   void helpRequested(OptionsSpecification<?> specification);

   void validate() throws ArgumentValidationException;
}
