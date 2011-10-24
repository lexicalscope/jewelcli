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
package uk.co.flamingpenguin.jewel.cli;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class UnparsedOptionSpecificationBuilder implements OptionSpecificationBuilder {
    private final ReflectedMethod m_method;
    private ReflectedClass<?> m_type;
    private boolean m_multiValued;
    private ReflectedMethod optionalityMethod;
    private String valueName;

    public UnparsedOptionSpecificationBuilder(final ReflectedMethod method) {
        m_method = method;
    }

    @Override public void setType(final ReflectedClass<?> type) {
        m_type = type;
    }

    public void setMultiValued(final boolean multiValued) {
        m_multiValued = multiValued;
    }

    public void setOptionalityMethod(final ReflectedMethod optionalityMethod) {
        this.optionalityMethod = optionalityMethod;
    }

    public void setValueName(final String valueName) {
        this.valueName = valueName;
    }

    public UnparsedOptionSpecification createOptionSpecification() {
        return new UnparsedOptionSpecificationImpl(
                valueName,
                m_type.classUnderReflection(),
                m_multiValued,
                m_method,
                optionalityMethod);
    }

}
