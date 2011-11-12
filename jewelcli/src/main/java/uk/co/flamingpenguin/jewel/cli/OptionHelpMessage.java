package uk.co.flamingpenguin.jewel.cli;

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

/**
 * BETA - may be altered or removed in future versions
 * 
 * @author Tim Wood
 */
public interface OptionHelpMessage {
    void startOptionalOption();

    void startMandatoryOption();

    void longName(String longName);

    void shortName(String shortName);

    void multiValuedWithCustomPattern(String pattern);

    void multiValuedWithCustomPattern();

    void singleValuedWithCustomPattern(String pattern);

    void singleValued();

    void endOptionalOption();

    void endOptionalOption(String description);

    void endMandatoryOption();

    void endMandatoryOption(String description);
}