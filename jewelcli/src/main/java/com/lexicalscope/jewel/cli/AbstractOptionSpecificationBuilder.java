package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

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

class AbstractOptionSpecificationBuilder implements OptionSpecificationBuilder {
    protected final ReflectedMethod method;
    protected ReflectedClass<?> type;
    protected ReflectedMethod optionalityMethod;
    protected String description;
    protected String pattern;
    protected List<String> defaultValue;
    protected boolean defaultToNull;
    protected boolean multiValued;
    protected boolean hidden;

    public AbstractOptionSpecificationBuilder(final ReflectedMethod method) {
        this.method = method;
    }

    @Override public final void setDescription(final String description) {
        this.description = description;
    }

    @Override public final void setOptionalityMethod(final ReflectedMethod optionalityMethod) {
        this.optionalityMethod = optionalityMethod;
    }

    @Override public final void setType(final ReflectedClass<?> type) {
        this.type = type;
    }

    @Override public final void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    @Override public final void setDefaultValue(final List<String> defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override public final void setDefaultToNull(final boolean defaultToNull) {
        this.defaultToNull = defaultToNull;
    }

    @Override public final void setMultiValued(final boolean multiValued) {
        this.multiValued = multiValued;
    }

    @Override public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
}
