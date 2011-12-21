/*
 * Copyright 2007 Tim Wood
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

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ConvertUnparsedSetterMethodToUnparsedOptionSpecification extends AbstractConvertMethodToOptionSpecification
        implements
        Converter<ReflectedMethod, UnparsedOptionSpecification> {

    public ConvertUnparsedSetterMethodToUnparsedOptionSpecification(final ReflectedClass<?> klass) {
        super(klass);
    }

    @Override public UnparsedOptionSpecification convert(final ReflectedMethod method) {
        return createUnparsedOptionSpecificationFrom(method);
    }
}
