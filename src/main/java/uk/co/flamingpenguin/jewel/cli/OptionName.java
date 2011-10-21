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

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class OptionName
{
    private final String canonicalIdentifier;
    private final List<String> longNames;
    private final List<String> shortNames;
    private final String description;

    OptionName(
            final ReflectedMethod method,
            final String canonicalIdentifier,
            final List<String> longNames,
            final List<String> shortNames,
            final String description)
    {
        this.canonicalIdentifier = canonicalIdentifier;
        this.longNames = longNames;
        for (final String longName : longNames) {
            if (longName.trim().isEmpty())
            {
                throw new OptionSpecificationException(String.format(
                        "option %s long name cannot be blank",
                        method));
            }
        }
        this.shortNames = shortNames;
        this.description = description;
    }

    String getCanonicalIdentifier() {
        return canonicalIdentifier;
    }

    List<String> getLongNames()
    {
        return longNames;
    }

    List<String> getShortNames()
    {
        return shortNames;
    }

    String getDescription()
    {
        return description;
    }
}
