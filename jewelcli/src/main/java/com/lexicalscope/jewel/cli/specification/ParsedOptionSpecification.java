package com.lexicalscope.jewel.cli.specification;

import com.lexicalscope.jewel.cli.ValidationErrorBuilder;

import java.util.List;


/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public interface ParsedOptionSpecification extends OptionSpecification {
    /**
     * Get all of the short names of this option. Short names are single
     * characters that will by prefixed by the user with "-".
     *
     * @return the short names of this option
     */
    List<String> getShortNames();

    /**
     * All the names of the option
     *
     * @return all the names of the option
     */
    List<String> getNames();

    /**
     * Does this option have a Short Name
     *
     * @return true if the options has a short name
     */
    boolean hasShortName();

    /**
     * Get the long name of this option. Long names are multiple characters that
     * will be prefixed by the user with "--".
     *
     * @return the long name of this option
     */
    List<String> getLongName();

    /**
     * The pattern that values must conform to
     *
     * @return the regular expression that values must conform to
     */
    String getPattern();

    boolean allowedValue(String value);

    /**
     * Does the option take any arguments?
     *
     * @return True if the the option takes at least one argument
     */
    boolean hasValue();

    /**
     * Is this option a request for help
     *
     * @return True if this option is a request for help
     */
    boolean isHelpOption();

    /**
     * @return option is a boolean option
     */
    boolean isBoolean();

    void reportMissingTo(ValidationErrorBuilder validationErrorBuilder);
}
