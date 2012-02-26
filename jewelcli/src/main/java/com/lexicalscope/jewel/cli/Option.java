/*
 * Copyright 2006 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lexicalscope.jewel.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags a method as an option
 *
 * @author Tim Wood
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface Option
{
    static final String stringToMarkNoDefault = "7acb394c-4c4f-4414-89df-28a62e785507?39858";

    /**
     * The long name of this option
     *
     * @return The long name of this option
     */
    String[] longName() default {};

    /**
     * The short name of this option
     *
     * @return The short name of this option
     */
    String[] shortName() default "";

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
    String[] defaultValue() default { stringToMarkNoDefault };

    /**
     * The default value is null. Java does not allow null values in
     * annotations. Setting this attribute to true will default the value of the
     * option to null.
     *
     * @return true iff the default value of the option should be null
     */
    boolean defaultToNull() default false;

    /**
     * Should help be displayed if this option is present.
     *
     * @return True if this option is a help option
     */
    boolean helpRequest() default false;

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
    int maximum() default -1;
}
