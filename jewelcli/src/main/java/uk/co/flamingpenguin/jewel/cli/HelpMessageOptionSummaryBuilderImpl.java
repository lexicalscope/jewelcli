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

class HelpMessageOptionSummaryBuilderImpl {
    final StringBuilder result = new StringBuilder();

    public void startOptionalOption() {
        result.append("[");
    }

    @Override public String toString()
    {
        return result.toString();
    }

    public void startMandatoryOption() {

    }

    private String sepatator = "";

    public void longName(final String longName) {
        result.append(sepatator).append("--").append(longName);
        sepatator = " ";
    }

    public void shortName(final String shortName) {
        result.append(" -").append(shortName);
    }

    private void multiValued() {
        result.append("...");
    }

    public void multiValuedWithCustomPattern(final String pattern) {
        singleValuedWithCustomPattern(pattern);
        multiValued();
    }

    public void multiValuedWithCustomPattern() {
        singleValued();
        multiValued();
    }

    public void singleValuedWithCustomPattern(final String pattern) {
        result.append(" /").append(pattern).append("/");
    }

    public void singleValued() {
        result.append(" value");
    }
}
