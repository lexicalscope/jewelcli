/*
 * Copyright 2006 Tim Wood
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

import static com.lexicalscope.fluentreflection.FluentReflection.type;

import com.lexicalscope.fluentreflection.ReflectedClass;

class CliInstanceImpl<O> extends AbstractCliImpl<O> {
    private final O options;

    public CliInstanceImpl(final O options) {
        this(options, (ReflectedClass<O>) type(options.getClass()));
    }

    public CliInstanceImpl(final O options, final ReflectedClass<O> type) {
        super(type, createOptionSpecification(type));
        this.options = options;
    }

    private static <O> OptionsSpecification<O> createOptionSpecification(final ReflectedClass<O> klass) {
        return InstanceOptionsSpecificationParser.<O>createOptionsSpecificationImpl(klass);
    }

    @Override protected ArgumentPresenterImpl<O> argumentPresenter(
            final ReflectedClass<O> klass,
            final OptionsSpecification<O> specification) {
        return new ArgumentPresenterImpl<O>(specification, new InstanceArgumentPresentingStrategy<O>(
                specification,
                klass,
                options));
    }
}
