//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package uk.co.flamingpenguin.jewel.cli;

import com.lexicalscope.fluentreflection.ReflectedMethod;

/**
 * Specifies an Option
 * 
 * @author t.wood
 */
interface OptionSpecification
{
    /**
     * Each argument to this option must conform to the type returned by this
     * method
     * 
     * @return the type that each argument must conform to
     */
    Class<?> getType();

    /**
     * Canonical identifier for the option
     * 
     * @return the canonical identifier for the option
     */
    String getCanonicalIdentifier();

    /**
     * Are multiple arguments allowed?
     * 
     * @return True iff the the option takes multiple arguments
     */
    boolean isMultiValued();

    /**
     * Is the argument optional
     * 
     * @return is the argument optional
     */
    boolean isOptional();

    ReflectedMethod getMethod();

    ReflectedMethod getOptionalityMethod();
}
