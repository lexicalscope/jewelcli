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

package com.lexicalscope.jewel.cli.validation;

import static com.lexicalscope.fluent.FluentDollar.$;
import static com.lexicalscope.fluent.adapters.PreventConversion.preventConversion;
import static com.lexicalscope.jewel.cli.specification.OptionSpecificationMatchers.mandatory;
import static com.lexicalscope.jewel.cli.validation.RawOptionMatchers.isLastOption;

import com.lexicalscope.fluent.map.FluentMap;
import com.lexicalscope.jewel.cli.ValidationErrorBuilder;
import com.lexicalscope.jewel.cli.specification.OptionSpecification;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ArgumentValidatorImpl<O> implements ArgumentValidator
{
    private final ValidationErrorBuilder validationErrorBuilder;

    private final Map<RawOption, List<String>> rawArguments;
    private final FluentMap<ParsedOptionSpecification, List<String>> validatedArguments = $.<ParsedOptionSpecification, List<String>>map();
    private final FluentMap<ParsedOptionSpecification, List<String>> validatedMandatoryArguments;

    private final List<String> validatedUnparsedArguments = new ArrayList<String>();

    private final OptionsSpecification<O> specification;

    public ArgumentValidatorImpl(
            final OptionsSpecification<O> specification,
            final ValidationErrorBuilder validationErrorBuilder)
    {
        this.specification = specification;
        this.validationErrorBuilder = validationErrorBuilder;

        rawArguments = $.<RawOption, List<String>>mapPipeline().
                 allowKeys(new KnownOptions(specification, validationErrorBuilder)).
                 processPuts(
                          isLastOption(),
                          new TrimExccessValues(specification, validatedUnparsedArguments)).
                 convertKeys(
                          preventConversion(ParsedOptionSpecification.class, RawOption.class),
                          new ConverterRawOptionToParsedOptionSpecification(specification)).
                 allowKeys(new RejectHelpOption(specification)).
                 vetoPuts(new ReportWrongNumberOfValues(validationErrorBuilder)).
                 vetoPuts(new ReportWrongFormatValues(validationErrorBuilder)).
                 transform(validatedArguments);

        validatedMandatoryArguments = $(validatedArguments).$retainKeys(mandatory());
    }

    @Override public void processOption(final String optionName, final List<String> values) {
        rawArguments.put(new RawOption(optionName), values);
    }

    @Override public void processLastOption(final String optionName, final List<String> values) {
        rawArguments.put(new RawOption(optionName, true), values);
    }

    @Override public void processUnparsed(final List<String> values) {
        validatedUnparsedArguments.addAll(values);
        validateUnparsedOptions();
    }

    @Override public OptionCollection finishedProcessing() {
        validationErrorBuilder.validate();

        specification.
           getMandatoryOptions().
              _removeAll(validatedMandatoryArguments.keySet()).
              _forEach(ParsedOptionSpecification.class).
              reportMissing(validationErrorBuilder);

        validationErrorBuilder.validate();

        return new OptionCollectionImpl(specification, validatedArguments, validatedUnparsedArguments);
    }

    private void validateUnparsedOptions()
    {
        if (specification.hasUnparsedSpecification())
        {
            final OptionSpecification argumentSpecification = specification.getUnparsedSpecification();

            if (argumentSpecification.isOptional() && validatedUnparsedArguments.isEmpty())
            {
                // OK
            }
            else if (!argumentSpecification.allowedThisManyValues(validatedUnparsedArguments.size()))
            {
                validationErrorBuilder.wrongNumberOfValues(argumentSpecification, validatedUnparsedArguments);
            }
        }
        else if (!validatedUnparsedArguments.isEmpty())
        {
            validationErrorBuilder.unexpectedTrailingValue(validatedUnparsedArguments);
        }
    }
}
