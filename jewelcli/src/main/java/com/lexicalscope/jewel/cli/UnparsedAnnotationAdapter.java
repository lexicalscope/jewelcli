package com.lexicalscope.jewel.cli;

import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.FluentMethod;

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
    private final Unparsed option;

    UnparsedAnnotationAdapter(
            final FluentClass<?> klass,
            final FluentMethod method,
            final Unparsed unparsed) {
        super(klass, method);
        this.option = unparsed;
    }

    @Override public String description() {
        return option.description().trim();
    }

    @Override public String pattern() {
        return option.pattern();
    }

    @Override public boolean defaultToNull() {
        return option.defaultToNull();
    }

    @Override public String[] defaultValue() {
        return option.defaultValue();
    }

    public String name() {
        return option.name();
    }

    @Override public boolean isHidden() {
        return option.hidden();
    }

    @Override public int minimum() {
        return option.minimum();
    }

    @Override public int exactly() {
        return option.exactly();
    }

    @Override public int maximum() {
        return option.maximum();
    }
}
