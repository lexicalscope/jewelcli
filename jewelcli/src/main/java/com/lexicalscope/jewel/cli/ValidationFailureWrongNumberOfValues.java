package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.jewel.JewelRuntimeException;


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

class ValidationFailureWrongNumberOfValues extends ValidationFailureImpl
{
    private static final long serialVersionUID = -7899339429456035393L;
    private final OptionSpecification specification;
    private final List<String> values;

    public ValidationFailureWrongNumberOfValues(final OptionSpecification specification, final List<String> values) {
        super(specification, formatMessage(specification, values));
        this.specification = specification;
        this.values = values;
    }

    private static String formatMessage(final OptionSpecification specification, final List<String> values) {
        return specification.compareCountToSpecification(values.size(), new SpecificationMultiplicity<String>(){
            @Override public String expectedNoneGotSome() {
                if(values.size() == 1)
                {
                    return String.format(ArgumentValidationException.m_messages.getString("validationError.UnexpectedValue"), values.get(0));
                }
                return String.format(ArgumentValidationException.m_messages.getString("validationError.UnexpectedValues"), values);
            }

            @Override public String expectedOneGotNone() {
                return String.format(ArgumentValidationException.m_messages.getString("validationError.MissingValue"));
            }

            @Override public String expectedOneGotSome() {
                return String.format(ArgumentValidationException.m_messages.getString("validationError.AdditionalValue"), values.subList(1, values.size()));
            }

            @Override public String expectedExactGotTooFew(final int exactly, final int valueCount) {
                if(exactly == 1)
                {
                    return String.format(ArgumentValidationException.m_messages.getString("validationError.LessValueThanExactly"), exactly, values);
                }
                return String.format(ArgumentValidationException.m_messages.getString("validationError.LessValuesThanExactly"), exactly, values);
            }

            @Override public String expectedExactGotTooMany(final int exactly, final int valueCount) {
                final List<String> subList = values.subList(exactly, values.size());
                if(exactly == 1) {
                    return String.format(ArgumentValidationException.m_messages.getString("validationError.MoreValueThanExactly"), exactly, subList);
                }
                return String.format(ArgumentValidationException.m_messages.getString("validationError.MoreValuesThanExactly"), exactly, subList);
            }

            @Override public String expectedMinimumGotTooFew(final int minimum, final int valueCount) {
                if(minimum == 1) {
                    return String.format(ArgumentValidationException.m_messages.getString("validationError.LessValueThanMinimum"), minimum, values);
                }
                return String.format(ArgumentValidationException.m_messages.getString("validationError.LessValuesThanMinimum"), minimum, values);
            }

            @Override public String expectedMaximumGotTooMany(final int maximum, final int valueCount) {
                final List<String> subList = values.subList(maximum, values.size());
                if(maximum == 1) {
                    return String.format(ArgumentValidationException.m_messages.getString("validationError.MoreValueThanMaximum"), maximum, subList);
                }
                return String.format(ArgumentValidationException.m_messages.getString("validationError.MoreValuesThanMaximum"), maximum, subList);
            }

            @Override public String allowed() {
                throw new JewelRuntimeException("unable to determine why the number of values is wrong " + specification + " with values (" + values.size() + ")");
            }});
    }

    @Override public ValidationFailureType getFailureType() {
        return specification.compareCountToSpecification(values.size(), new SpecificationMultiplicity<ValidationFailureType>(){
            @Override public ValidationFailureType expectedNoneGotSome() {
                return ValidationFailureType.UnexpectedValue;
            }

            @Override public ValidationFailureType expectedOneGotNone() {
                return ValidationFailureType.MissingValue;
            }

            @Override public ValidationFailureType expectedOneGotSome() {
                return ValidationFailureType.UnexpectedAdditionalValue;
            }

            @Override public ValidationFailureType expectedExactGotTooFew(final int exactly, final int valueCount) {
                return ValidationFailureType.TooFewValues;
            }

            @Override public ValidationFailureType expectedExactGotTooMany(final int exactly, final int valueCount) {
                return ValidationFailureType.TooManyValues;
            }

            @Override public ValidationFailureType expectedMinimumGotTooFew(final int minimum, final int valueCount) {
                return ValidationFailureType.TooFewValues;
            }

            @Override public ValidationFailureType expectedMaximumGotTooMany(final int maximum, final int valueCount) {
                return ValidationFailureType.TooManyValues;
            }

            @Override public ValidationFailureType allowed() {
                throw new JewelRuntimeException("unable to determine why the number of values is wrong " + specification + " with values (" + values.size() + ")");
            }});
    }
}