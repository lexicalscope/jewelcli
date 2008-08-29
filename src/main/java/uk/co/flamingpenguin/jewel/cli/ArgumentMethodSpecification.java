package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

interface ArgumentMethodSpecification extends OptionArgumentsSpecification
{
   /**
    * The method which is used to determine the optionality
    * of the argument.
    *
    * @return get the optionality method
    */
   Method getOptionalityMethod();
}