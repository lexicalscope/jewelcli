//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

public class ConverterParsedOptionSpecificationToRawOption implements Converter<ParsedOptionSpecification, String>
{
   @Override
   public String convert(final ParsedOptionSpecification from)
   {
      return from.getLongName().get(0);
   }
}
