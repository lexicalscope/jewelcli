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
package uk.co.flamingpenguin.jewel.cli;

import static uk.co.flamingpenguin.jewel.cli.OptionsSpecificationImpl.nullOrBlank;

import com.lexicalscope.fluentreflection.ReflectedMethod;

class UnparsedOptionSpecificationImpl implements UnparsedOptionSpecification {
    private final ReflectedMethod method;
    private final ReflectedMethod optionalityMethod;
    private final String valueName;
    private final Class<?> type;
    private final boolean multiValued;

    public UnparsedOptionSpecificationImpl(
            final String valueName,
            final Class<?> type,
            final boolean multiValued,
            final ReflectedMethod method,
            final ReflectedMethod optionalityMethod) {
        this.valueName = valueName;
        this.type = type;
        this.multiValued = multiValued;
        this.method = method;
        this.optionalityMethod = optionalityMethod;
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

    @Override public String toString() {
        return new UnparsedOptionSummary(this).toString();
    }
}
