//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import static java.lang.Math.min;

import com.lexicalscope.fluent.FluentDollar;
import com.lexicalscope.fluent.adapters.ConverterProcessor;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;

import java.util.List;
import java.util.Map.Entry;

public class TrimExccessOptions implements ConverterProcessor<Entry<RawOption, List<String>>>
{
   private final OptionsSpecification<?> specification;
   private final List<String> validatedUnparsedArguments;

   public TrimExccessOptions(final OptionsSpecification<?> specification, final List<String> validatedUnparsedArguments) {
      this.specification = specification;
      this.validatedUnparsedArguments = validatedUnparsedArguments;
   }

   @Override
   public Entry<RawOption, List<String>> convert(final Entry<RawOption, List<String>> from) {
      final List<String> fromValues = from.getValue();
      List<String> convertedValues;
      if (!specification.hasUnparsedSpecification()) {
         convertedValues = fromValues;
      } else {
         final int maximumArgumentConsumption = min(fromValues.size(), specification.getSpecification(from.getKey().stringValue()).maximumArgumentConsumption());
         validatedUnparsedArguments.addAll(0, fromValues.subList(maximumArgumentConsumption, fromValues.size()));
         convertedValues = FluentDollar._(fromValues.subList(0, maximumArgumentConsumption));
      }

      return FluentDollar.$.mapEntry(from.getKey(), convertedValues);
   }
}
