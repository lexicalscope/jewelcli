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

interface OptionSpecificationBuilder {
    void setPattern(String pattern);
    void setDescription(String description);
    void setOptionalityMethod(ReflectedMethod optionalityMethod);
    void setType(ReflectedClass<?> type);
    void setDefaultValue(List<String> defaultValue);
    void setDefaultToNull(boolean defaultToNull);
    void setMultiValued(boolean multiValued);
}