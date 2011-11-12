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

class HelpMessageBuilderImpl implements HelpMessage {
    private final StringBuilder message = new StringBuilder();

    @Override public void noUsageInformation() {
        message.append("The options available are:");
    }

    @Override public void hasUsageInformation(final String applicationName) {
        hasUsageInformation();
        message.append(String.format("%s ", applicationName));
    }

    @Override public void hasUsageInformation() {
        message.append("Usage: ");
    }

    @Override public void hasOnlyOptionalOptions() {
        message.append("[");
        message.append("options");
        message.append("]");
    }

    @Override public void hasSomeMandatoryOptions() {
        message.append("options");
    }

    @Override public void hasUnparsedMultiValuedOption(final String valueName) {
        message.append(" ");
        message.append(valueName);
        message.append("...");
    }

    @Override public void hasUnparsedOption(final String valueName) {
        message.append(" ");
        message.append(valueName);
    }

    private final String lineSeparator = System.getProperty("line.separator");
    private String separator = "";

    @Override public void startOfOptions() {
        message.append(lineSeparator);
    }

    @Override public OptionHelpMessage option() {
        message.append(separator).append("\t");
        separator = lineSeparator;
        return new HelpMessageOptionSummaryBuilderImpl(message);
    }

    @Override public void endOfOptions() {
        // OK
    }

    @Override public String toString() {
        return message.toString();
    }
}
