/*
 * Copyright 2007 Tim Wood
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
package uk.co.flamingpenguin.jewel.cli;

/**
 * The specification of the CLI. This interface is still in development, and may
 * be subject to incompatible changes.
 * 
 * @author tim
 */
interface CliSpecification extends Iterable<ParsedOptionSpecification>
{
    /**
     * The name of the application as specified in the
     * <code>CommandLineInterface</code> annotation
     * 
     * @see uk.co.flamingpenguin.jewel.cliCommandLineInterface
     * 
     * @return The name of the application
     */
    String getApplicationName();

    /**
     * Has anything been specified to accept additional unparsed arguments?
     * 
     * @return Has anything been specified to accept additional unparsed
     *         arguments?
     */
    boolean hasUnparsedSpecification();

    /**
     * The specification for any additional unparsed arguments, if any have been
     * specified
     * 
     * @return The specification for any additional unparsed arguments
     */
    UnparsedOptionSpecification getUnparsedSpecification();
}
