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

        if(hasExactCount() && exactly() < minimum() || exactly() > maximum())
        {
            throw new InvalidOptionSpecificationException("option has maximum and minimum and exact count which can never be satisfied: "
                    + annotation.method());
        }
        else if(minimum() > maximum())
        {
            throw new InvalidOptionSpecificationException("minimum cannot be greater than maximum: "
                    + annotation.method());
        }
        else if(maximum() < 0)
        {
            throw new InvalidOptionSpecificationException("maximum must not be less than zero: "
                    + annotation.method());
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
        if(count == 0 && !hasValue())
        {
            return true;
        }
        else if(count == 1 && hasValue() && !isMultiValued())
        {
            return true;
        }
        else if(isMultiValued() && hasExactCount())
        {
            return count == exactly();
        }
        else if(isMultiValued())
        {
            return minimum() <= count && count <= maximum();
        }
        return false;
    }

    public final int maximum() {
        return annotation.maximum();
    }

    public final int minimum() {
        return annotation.minimum();
    }

    public final int exactly() {
        return annotation.exactly();
    }

    public final boolean hasExactCount() {
        return annotation.exactly() >= 0;
    }

    @Override public <T> T compareCountToSpecification(
            final int valueCount,
            final SpecificationMultiplicity<T> specificationMultiplicity) {
        if(!hasValue() && valueCount > 0) {
            return specificationMultiplicity.expectedNoneGotSome();
        } else if(!isMultiValued() && hasValue() && valueCount == 0) {
            return specificationMultiplicity.expectedOneGotNone();
        } else if(!isMultiValued() && valueCount > 1) {
            return specificationMultiplicity.expectedOneGotSome();
        } else if(isMultiValued()) {
            if(hasExactCount() && valueCount != exactly()) {
                if(valueCount < exactly()) {
                    return specificationMultiplicity.expectedExactGotTooFew(exactly(), valueCount);
                } else {
                    return specificationMultiplicity.expectedExactGotTooMany(exactly(), valueCount);
                }
            } else if(valueCount < minimum()) {
                return specificationMultiplicity.expectedMinimumGotTooFew(minimum(), valueCount);
            } else if(valueCount > maximum()) {
                return specificationMultiplicity.expectedMaximumGotTooMany(maximum(), valueCount);
            }
        }
        return specificationMultiplicity.allowed();
    }

    @Override public int maximumArgumentConsumption() {
        if(isMultiValued()) {
            if(hasExactCount()) {
                return exactly();
            } else {
                return maximum();
            }
        } else if (hasValue()) {
            return 1;
        }
        return 0;
    }
}
