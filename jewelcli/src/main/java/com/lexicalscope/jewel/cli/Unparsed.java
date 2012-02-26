package com.lexicalscope.jewel.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags a method as returning any unparsed arguments
 *
 * @author Tim Wood
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface Unparsed
{
    /**
     * The name to use when describe the unparsed arguments in help text and
     * error messages
     *
     * @return The name to use when describe the unparsed arguments in help text
     *         and error messages
     */
    String name() default "ARGUMENTS";

    /**
     * The regexp that the values of this option must match
     *
     * @return The regexp that the values of this option must match
     */
    String pattern() default ".*";

    /**
     * A description of this option
     *
     * @return A description of this option
     */
    String description() default "";

    /**
     * The default value if none is specified
     *
     * @return The value to present if none is specified
     */
    String[] defaultValue() default { Option.stringToMarkNoDefault };

    /**
     * The default value is null. Java does not allow null values in
     * annotations. Setting this attribute to true will default the value of the
     * option to null.
     *
     * @return true iff the default value of the option should be null
     */
    boolean defaultToNull() default false;

    /**
     * Option is not displayed in help messages. Probably best not to use this with mandatory options.
     *
     * @return the option is not displayed in any help messages
     */
    boolean hidden() default false;

    /**
     * Multivalued option must have at least this many values
     *
     * @return Multivalued option must have at least this many values
     */
    int minimum() default -1;

    /**
     * Multivalued option must have exactly this many values
     *
     * @return Multivalued option must have exactly this many values
     */
    int exactly() default -1;

    /**
     * Multivalued option can have at most this many values
     *
     * @return Multivalued option can have at most this many values
     */
    int maximum() default Integer.MAX_VALUE;
}
