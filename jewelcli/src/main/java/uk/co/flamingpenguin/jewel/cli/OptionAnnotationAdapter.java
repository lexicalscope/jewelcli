package uk.co.flamingpenguin.jewel.cli;

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

class OptionAnnotationAdapter extends AbstractOptionAdapter {
    private final Option option;

    OptionAnnotationAdapter(
            final ReflectedClass<?> klass,
            final ReflectedMethod method,
            final Option option) {
        super(klass, method);
        this.option = option;
    }

    @Override public String description() {
        return option.description();
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
}
