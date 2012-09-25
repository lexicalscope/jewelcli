//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import com.lexicalscope.fluent.functions.BiConverter;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

public class ConverterRawOptionToParsedOptionSpecification implements BiConverter<ParsedOptionSpecification, RawOption>
{
   private final OptionsSpecification<?> specification;

   public ConverterRawOptionToParsedOptionSpecification(final OptionsSpecification<?> specification)
   {
      this.specification = specification;
   }

   @Override
   public RawOption forward(@SuppressWarnings("unused") final ParsedOptionSpecification from)
   {
      throw new IllegalStateException("there is not unique conversion from an option to a string");
   }

   @Override
   public ParsedOptionSpecification reverse(final RawOption from)
   {
      return specification.getSpecification(from.stringValue());
   }
}
