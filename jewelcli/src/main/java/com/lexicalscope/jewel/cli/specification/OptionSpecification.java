//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.specification;

import java.util.List;

import com.lexicalscope.fluentreflection.FluentMethod;

/**
 * Specifies an Option
 *
 * BETA: unstable may change in future versions
 *
 * @author t.wood
 */
public interface OptionSpecification
{
    /**
     * Get a description of the option. The description can be specified in the
     * <code>Option</code> annotation
     *
     * @see com.lexicalscope.jewel.cli.Option
     *
     * @return a description of the option
     */
    String getDescription();

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
     * Does the option have a value
     *
     * @return true iff the option has at least one value
     */
    boolean hasValue();

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
     * @return true iff this option has a default value
     */
    boolean hasDefaultValue();

    /**
     * Is the option hidden from help messages?
     *
     * @return true iff the option is hidden from help messages
     */
    boolean isHidden();

    /**
     * Is the option allowed to have this many values?
     *
     * @param count the number of values that the option might have
     *
     * @return true iff the count is a valid number of values for this option
     */
    boolean allowedThisManyValues(int count);

    FluentMethod getMethod();

    FluentMethod getOptionalityMethod();

    boolean hasExactCount();

    int exactly();

    int minimum();

    int maximum();

    int maximumArgumentConsumption();

    <T> T compareCountToSpecification(int valueCount, SpecificationMultiplicity<T> specificationMultiplicity);
}
