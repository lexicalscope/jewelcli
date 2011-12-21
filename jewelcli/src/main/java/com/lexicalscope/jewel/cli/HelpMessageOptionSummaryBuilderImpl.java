package com.lexicalscope.jewel.cli;

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

class HelpMessageOptionSummaryBuilderImpl implements OptionHelpMessage {
    final StringBuilder result;

    public HelpMessageOptionSummaryBuilderImpl(final StringBuilder message) {
        result = message;
    }

    public HelpMessageOptionSummaryBuilderImpl() {
        this(new StringBuilder());
    }

    @Override public void startOptionalOption() {
        result.append("[");
    }

    @Override public void startMandatoryOption() {

    }

    @Override public void longName(final List<String> longNames) {
        String sepatator = "";
        for (final String longName : longNames) {
            result.append(sepatator).append("--").append(longName);
            sepatator = " ";
        }
    }

    @Override public void shortName(final List<String> shortNames) {
        for (final String shortName : shortNames) {
            result.append(" -").append(shortName);
        }
    }

    private void multiValued() {
        result.append("...");
    }

    @Override public void multiValuedWithCustomPattern(final String pattern) {
        singleValuedWithCustomPattern(pattern);
        multiValued();
    }

    @Override public void multiValuedWithCustomPattern() {
        singleValued();
        multiValued();
    }

    @Override public void singleValuedWithCustomPattern(final String pattern) {
        result.append(" /").append(pattern).append("/");
    }

    @Override public void singleValued() {
        result.append(" value");
    }

    @Override public void noValued() {
        // OK
    }

    @Override public void endOptionalOption() {
        result.append("]");
    }

    @Override public void endOptionalOption(final String description) {
        endOptionalOption();
        optionDescription(description);
    }

    @Override public void endMandatoryOption() {

    }

    @Override public void endMandatoryOption(final String description) {
        optionDescription(description);
    }

    private void optionDescription(final String description) {
        result.append(" : ").append(description);
    }

    @Override public String toString()
    {
        return result.toString();
    }
}
