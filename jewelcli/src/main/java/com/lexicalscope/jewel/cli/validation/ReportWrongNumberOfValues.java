//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import com.lexicalscope.fluent.map.PutVeto;
import com.lexicalscope.jewel.cli.ValidationErrorBuilder;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

import java.util.List;

public class ReportWrongNumberOfValues implements PutVeto<ParsedOptionSpecification, List<String>>
{
   private final ValidationErrorBuilder validationErrorBuilder;

   public ReportWrongNumberOfValues(final ValidationErrorBuilder validationErrorBuilder)
   {
      this.validationErrorBuilder = validationErrorBuilder;
   }

   @Override
   public boolean allow(final ParsedOptionSpecification key, final List<String> values)
   {
      if (!key.allowedThisManyValues(values.size()))
      {
          validationErrorBuilder.wrongNumberOfValues(key, values);
          return false;
      }
      return true;
   }
}
