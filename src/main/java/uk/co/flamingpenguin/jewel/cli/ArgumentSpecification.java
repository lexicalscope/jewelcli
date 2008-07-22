package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

interface ArgumentSpecification
{
   String getName();
   Method getMethod();
   
   /**
    * The method which is used to determine the optionality
    * of the argument.
    * 
    * @return get the optionality method
    */
   Method getOptionalityMethod();
   
   Class<?> getType();
   boolean isMultiValued();
   
   /**
    * Is the argument optional
    * 
    * @return is the argument optional
    */
   boolean isOptional();
}