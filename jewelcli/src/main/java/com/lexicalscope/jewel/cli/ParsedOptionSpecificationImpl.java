/*
 * Copyright 2009 Tim Wood
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

import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

import java.util.ArrayList;
import java.util.List;

class ParsedOptionSpecificationImpl extends AbstractOptionSpecification implements ParsedOptionSpecification {
    private final OptionAnnotationAdapter optionAnnotation;

    ParsedOptionSpecificationImpl(final OptionAnnotationAdapter annotation) {
        super(annotation);
        optionAnnotation = annotation;
        for (final String longName : annotation.longName()) {
            if (longName.trim().isEmpty())
            {
                throw new InvalidOptionSpecificationException(String.format(
                        "option %s long name cannot be blank",
                        getMethod()));
            }
        }
    }

    @Override public List<String> getLongName() {
        return optionAnnotation.longName();
    }

    @Override public List<String> getShortNames() {
        return optionAnnotation.shortName();
    }

    @Override public List<String> getNames() {
        final List<String> result = new ArrayList<String>(getShortNames());
        result.addAll(getLongName());
        return result;
    }

    @Override public boolean hasShortName() {
        return !getShortNames().isEmpty();
    }

    @Override public boolean isHelpOption() {
        return optionAnnotation.helpRequest();
    }

    @Override public String getPattern() {
        return annotation.pattern();
    }

    @Override public boolean hasValue() {
        return !isBoolean();
    }

    @Override public final boolean isBoolean() {
        return getType().isAssignableFrom(Boolean.class) || getType().isAssignableFrom(boolean.class);
    }

    @Override public String toString() {
        return new ParsedOptionSummary(this).toString();
    }

    @Override public boolean allowedValue(final String value) {
        return value.matches(getPattern());
    }

    @Override public void reportMissing(final ValidationErrorBuilder validationErrorBuilder) {
       validationErrorBuilder.missingOption(this);
    }
}
