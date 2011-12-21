package com.lexicalscope.jewel.cli;

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

final class UnparsedAnnotationAdapter extends AbstractOptionAdapter {
    private final Unparsed unparsed;

    UnparsedAnnotationAdapter(
            final ReflectedClass<?> klass,
            final ReflectedMethod method,
            final Unparsed unparsed) {
        super(klass, method);
        this.unparsed = unparsed;
    }

    @Override public String description() {
        return unparsed.description();
    }

    @Override public String pattern() {
        return unparsed.pattern();
    }

    @Override public boolean defaultToNull() {
        return unparsed.defaultToNull();
    }

    @Override public String[] defaultValue() {
        return unparsed.defaultValue();
    }

    public String name() {
        return unparsed.name();
    }
}
