package uk.co.flamingpenguin.jewel.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Associates properties with a Command Line Interface
 * 
 * @deprecated please use {@link com.lexicalscope.jewel.cli.CommandLineInterface} instead
 * 
 * @author Tim Wood
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandLineInterface
{
    /**
     * The name of the application that this is the interface for
     *
     * @return The name of the application that this is the interface for
     */
    String application() default "";
}
