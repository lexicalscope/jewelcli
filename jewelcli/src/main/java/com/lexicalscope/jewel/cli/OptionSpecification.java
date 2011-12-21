//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package com.lexicalscope.jewel.cli;

import java.util.List;

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

    /**
     * Get the default values which will be used if the option is not specified
     * by the user.
     * 
     * @return The default values which will be used if the option is not
     *         present
     */
    List<String> getDefaultValue();

    /**
     * Is there a default value to use if this option is not present? Options
     * with a default value are assumed to be optional.
     * 
     * @return True iff this option has a default value
     */
    boolean hasDefaultValue();

    ReflectedMethod getMethod();

    ReflectedMethod getOptionalityMethod();
}
