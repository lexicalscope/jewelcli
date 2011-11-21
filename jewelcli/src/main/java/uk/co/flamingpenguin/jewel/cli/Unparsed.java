package uk.co.flamingpenguin.jewel.cli;

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
    String name() default "";
    //
    //    /**
    //     * The regexp that the values of this option must match
    //     * 
    //     * @return The regexp that the values of this option must match
    //     */
    //    String pattern() default ".*";
    //
    //    /**
    //     * A description of this option
    //     * 
    //     * @return A description of this option
    //     */
    //    String description() default "";
    //
    //    /**
    //     * The default value if none is specified
    //     * 
    //     * @return The value to present if none is specified
    //     */
    //    String[] defaultValue() default { Option.stringToMarkNoDefault };
}
