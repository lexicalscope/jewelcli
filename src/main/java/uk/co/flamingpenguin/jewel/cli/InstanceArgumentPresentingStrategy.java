package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;

import java.util.Map;

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

class InstanceArgumentPresentingStrategy<O> implements ArgumentPresentingStrategy<O> {
    private final O options;
    private final ReflectedClass<O> klass;
    private final OptionsSpecification<O> specification;

    public InstanceArgumentPresentingStrategy(
            final OptionsSpecification<O> specification,
            final ReflectedClass<O> klass,
            final O options) {
        this.specification = specification;
        this.klass = klass;
        this.options = options;
    }

    @Override public O presentArguments(final Map<String, Object> argumentMap) {
        System.out.println(argumentMap);
        for (final ReflectedMethod reflectedMethod : klass.methods(annotatedWith(Option.class))) {
            final boolean isBoolean = specification.getSpecification(reflectedMethod).isBoolean();
            setValueOnOptions(argumentMap, reflectedMethod, isBoolean);
        }
        for (final ReflectedMethod reflectedMethod : klass.methods(annotatedWith(Unparsed.class))) {
            setValueOnOptions(argumentMap, reflectedMethod, false);
        }
        return options;
    }

    private void setValueOnOptions(
            final Map<String, Object> argumentMap,
            final ReflectedMethod reflectedMethod,
            final boolean isBoolean) {
        if (argumentMap.containsKey(reflectedMethod.propertyName()))
        {
            if (isBoolean)
            {
                reflectedMethod.call(options, argumentMap.containsKey(reflectedMethod.propertyName()));
            }
            else if (argumentMap.get(reflectedMethod.propertyName()) != null)
            {
                reflectedMethod.call(options, argumentMap.get(reflectedMethod.propertyName()));
            }
        }
    }
}
