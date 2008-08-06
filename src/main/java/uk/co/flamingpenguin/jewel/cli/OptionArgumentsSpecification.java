//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package uk.co.flamingpenguin.jewel.cli;

/**
 * Specifies the arguments which are expected by a particular
 * option.
 *
 * @author t.wood
 */
interface OptionArgumentsSpecification
{
   /**
    * Each argument to this option must conform to the
    * type returned by this method
    *
    * @return the type that each argument must conform to
    */
   Class<?> getType();

   /**
    * Does the option take any arguments?
    *
    * @return True iff the the option takes at least one argument
    */
   boolean hasValue();

   /**
    * Are multiple arguments allowed?
    *
    * @return True iff the the option takes at multiple arguments
    */
   boolean isMultiValued();

   /**
    * Is the argument optional
    *
    * @return is the argument optional
    */
   boolean isOptional();
}
