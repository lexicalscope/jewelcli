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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Unparsed
{
   /**
    * The name to use when describe the unparsed arguments in help text and error messages
    *
    * @return The name to use when describe the unparsed arguments in help text and error messages
    */
   String name() default "";
}
