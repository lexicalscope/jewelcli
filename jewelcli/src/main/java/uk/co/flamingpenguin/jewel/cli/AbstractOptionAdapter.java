package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.util.Collection;
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

abstract class AbstractOptionAdapter implements OptionAdapter {
    private final ReflectedClass<?> klass;
    private final ReflectedMethod method;
    private final ReflectedClass<?> methodType;

    AbstractOptionAdapter(
            final ReflectedClass<?> klass,
            final ReflectedMethod method) {
        this.klass = klass;
        this.method = method;
        if (isMutator().matches(method))
        {
            this.methodType = method.argumentTypes().get(0);
        }
        else
        {
            this.methodType = method.returnType();
        }
    }

    @Override public final ReflectedMethod method() {
        return method;
    }

    @Override public final ReflectedMethod correspondingOptionalityMethod() {
        if (isMutator().matches(method))
        {
            return null;
        }

        final List<ReflectedMethod> methods =
                klass.methods(
                        callableHasName(addPrefix("is", method.propertyName())).and(isExistence()));
        if (!methods.isEmpty()) {
            return methods.get(0);
        }
        return null;
    }

    private String addPrefix(final String prefix, final String name) {
        return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override public final boolean isMultiValued() {
        return methodType.isType(reflectedTypeReflectingOn(Collection.class));
    }

    @Override public final ReflectedClass<? extends Object> getValueType() {
        final ReflectedClass<? extends Object> valueType =
                isMultiValued()
                        ? methodType.asType(reflectedTypeReflectingOn(Collection.class)).typeArgument(0)
                        : methodType;

        return reflectedTypeReflectingOn(Object.class).matches(valueType)
                ? type(String.class)
                : valueType;
    }

    @Override public final boolean hasDefaultValue() {
        return !(defaultValue().length == 1 && defaultValue()[0].equals(Option.stringToMarkNoDefault));
    }
}
