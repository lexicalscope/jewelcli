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

import static com.lexicalscope.jewel.cli.OptionsSpecificationImpl.nullOrBlank;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class UnparsedOptionSpecificationImpl implements UnparsedOptionSpecification {
    private final ReflectedMethod method;
    private final ReflectedMethod optionalityMethod;
    private final String valueName;
    private final Class<?> type;
    private final boolean multiValued;
    private final List<String> defaultValue;
    private final boolean defaultToNull;
    private final boolean hidden;

    public UnparsedOptionSpecificationImpl(
            final String valueName,
            final Class<?> type,
            final boolean multiValued,
            final ReflectedMethod method,
            final ReflectedMethod optionalityMethod,
            final List<String> defaultValue,
            final boolean defaultToNull,
            final boolean hidden) {
        this.valueName = valueName;
        this.type = type;
        this.multiValued = multiValued;
        this.method = method;
        this.optionalityMethod = optionalityMethod;
        this.defaultValue = defaultValue;
        this.defaultToNull = defaultToNull;
        this.hidden = hidden;
    }

    @Override public Class<?> getType() {
        return type;
    }

    @Override public boolean isMultiValued() {
        return multiValued;
    }

    @Override public boolean isOptional() {
        return optionalityMethod != null;
    }

    @Override public String getCanonicalIdentifier() {
        return method.propertyName();
    }

    @Override public ReflectedMethod getMethod() {
        return method;
    }

    @Override public ReflectedMethod getOptionalityMethod() {
        return optionalityMethod;
    }

    @Override public String getValueName() {
        return nullOrBlank(valueName) ? "ARGUMENTS" : valueName;
    }

    @Override public List<String> getDefaultValue() {
        return defaultValue;
    }

    @Override public boolean hasDefaultValue() {
        return defaultValue != null || defaultToNull;
    }

    @Override public String toString() {
        return new UnparsedOptionSummary(this).toString();
    }

    @Override public boolean isHidden() {
        return hidden;
    }
}
