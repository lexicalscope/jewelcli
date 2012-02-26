package com.lexicalscope.jewel.cli;

import static java.util.Arrays.asList;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

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

abstract class AbstractOptionSpecification implements OptionSpecification, Comparable<OptionSpecification> {
    protected final OptionAdapter annotation;
    private final List<String> defaultValue;

    public AbstractOptionSpecification(final OptionAdapter annotation) {
        this.annotation = annotation;

        if (annotation.defaultToNull() && annotation.hasDefaultValue())
        {
            throw new InvalidOptionSpecificationException("option cannot have null default and non-null default value: "
                    + annotation.method());
        }
        else if (annotation.defaultToNull())
        {
            if (annotation.isMultiValued())
            {
                defaultValue = null;
            }
            else
            {
                defaultValue = asList((String) null);
            }
        }
        else if (annotation.hasDefaultValue())
        {
            defaultValue = asList(annotation.defaultValue());
        }
        else
        {
            defaultValue = null;
        }
    }

    @Override public final List<String> getDefaultValue() {
        return defaultValue;
    }

    @Override public final boolean hasDefaultValue() {
        return getDefaultValue() != null || annotation.defaultToNull();
    }

    @Override public final String getDescription() {
        return annotation.description();
    }

    @Override public final Class<?> getType() {
        return annotation.getValueType().classUnderReflection();
    }

    @Override public final boolean isMultiValued() {
        return annotation.isMultiValued();
    }

    @Override public final boolean isOptional() {
        return getOptionalityMethod() != null || isBoolean() || hasDefaultValue();
    }

    public abstract boolean isBoolean();

    @Override public final String getCanonicalIdentifier() {
        return getMethod().propertyName();
    }

    @Override public final ReflectedMethod getMethod() {
        return annotation.method();
    }

    @Override public final ReflectedMethod getOptionalityMethod() {
        return annotation.correspondingOptionalityMethod();
    }

    @Override public final int compareTo(final OptionSpecification other) {
        return getCanonicalIdentifier().compareTo(other.getCanonicalIdentifier());
    }

    @Override public final boolean isHidden() {
        return annotation.isHidden();
    }

    @Override public boolean allowedThisManyValues(final int count) {
        // TODO Auto-generated method stub
        return false;
    }
}
