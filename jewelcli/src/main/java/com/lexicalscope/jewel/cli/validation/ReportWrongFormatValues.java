//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import com.lexicalscope.fluent.map.PutVeto;
import com.lexicalscope.jewel.cli.ValidationErrorBuilder;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

import java.util.List;

public class ReportWrongFormatValues implements PutVeto<ParsedOptionSpecification, List<String>>
{
   private final ValidationErrorBuilder validationErrorBuilder;

   public ReportWrongFormatValues(final ValidationErrorBuilder validationErrorBuilder)
   {
      this.validationErrorBuilder = validationErrorBuilder;
   }

   @Override
   public boolean allow(final ParsedOptionSpecification key, final List<String> values)
   {
      boolean result = true;
      for (final String value : values)
      {
          if (!key.allowedValue(value))
          {
              validationErrorBuilder.patternMismatch(key, value);
              result = false;
          }
      }
      return result;
   }
}
