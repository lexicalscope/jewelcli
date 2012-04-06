package com.lexicalscope.jewel.cli.parser;

import com.lexicalscope.jewel.cli.validation.ArgumentValidator;
import com.lexicalscope.jewel.cli.validation.OptionCollection;

/*
 * Copyright 2012 Tim Wood
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

/**
 * Arguments are registered with this interface by the parser
 *
 * BETA: unstable may change in future versions
 *
 * @author tim
 */
public interface ParsedArguments {
    /**
     * A value has been discovered
     *
     * @param value the option that has been discovered
     */
    void addValue(String value);

    /**
     * An option has been discovered
     *
     * @param option the option that has been recognised
     */
    void addOption(String option);

    /**
     * The remaining options are not parsed
     */
    void unparsedOptionsFollow();

    /**
     * Process each option and its values
     *
     * @param argumentProcessor the processor to apply to the arguments
     *
     * @return the parsed options
     */
    OptionCollection processArguments(ArgumentValidator argumentProcessor);
}