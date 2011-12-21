package com.lexicalscope.jewel.cli;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.lexicalscope.jewel.cli.ConvertTypeOfObject.converterTo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.lexicalscope.fluentreflection.TypeToken;

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

public class TestConvertTypeOfObject {
    private final ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilderImpl();
    private OptionSpecification specification;

    @Test public void argumentConversionTakesPlaceOnGet() throws Exception {
        assertThat(converterTo(validationErrorBuilder, specification, Integer.class).convert("14"), equalTo(14));
    }

    @Test public void argumentConversionCanConvertFromStringToPrimitive() throws Exception {
        assertThat(converterTo(validationErrorBuilder, specification, int.class).convert("14"), equalTo(14));
    }

    @Test public void argumentConversionCanConvertFromStringToChar() throws Exception {
        assertThat(converterTo(validationErrorBuilder, specification, char.class).convert("c"), equalTo('c'));
    }

    @Test(expected = CliValidationException.class) public void argumentConversionCannotConvertFromStringToChar()
            throws Exception {
        assertThat(converterTo(validationErrorBuilder, specification, char.class).convert("cc"), equalTo(null));

        validationErrorBuilder.validate();
    }

    @Test public void argumentConversionTakesPlaceOnGetOfIterable() throws Exception {
        assertThat(
                converterTo(validationErrorBuilder, specification, new TypeToken<Iterable<Integer>>() {}).convert(
                        asList("14", "15", "16")),
                contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfCollection() throws Exception {
        assertThat(
                converterTo(validationErrorBuilder, specification, new TypeToken<Collection<Integer>>() {}).convert(
                        asList("14", "15", "16")),
                contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfList() throws Exception {
        assertThat(
                converterTo(validationErrorBuilder, specification, new TypeToken<List<Integer>>() {}).convert(
                        asList("14", "15", "16")),
                contains(14, 15, 16));
    }

    @Test public void argumentConversionTakesPlaceOnGetOfSet() throws Exception {
        assertThat(
                converterTo(validationErrorBuilder, specification, new TypeToken<Set<Integer>>() {}).convert(
                        newHashSet("14", "15", "16")),
                contains(14, 15, 16));
    }
}
