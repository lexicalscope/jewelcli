//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

public class ConverterRawOptionToParsedOptionSpecification implements Converter<RawOption, ParsedOptionSpecification>
{
   private final OptionsSpecification<?> specification;

   public ConverterRawOptionToParsedOptionSpecification(final OptionsSpecification<?> specification)
   {
      this.specification = specification;
   }

   @Override
   public ParsedOptionSpecification convert(final RawOption from)
   {
      return specification.getSpecification(from.stringValue());
   }
}
