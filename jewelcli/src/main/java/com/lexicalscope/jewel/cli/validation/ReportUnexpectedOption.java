//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import com.lexicalscope.jewel.cli.ValidationErrorBuilder;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ReportUnexpectedOption extends TypeSafeMatcher<RawOption>
{
   private final OptionsSpecification<?> specification;
   private final ValidationErrorBuilder validationErrorBuilder;

   public ReportUnexpectedOption(final OptionsSpecification<?> specification, final ValidationErrorBuilder validationErrorBuilder)
   {
      this.specification = specification;
      this.validationErrorBuilder = validationErrorBuilder;
   }

   @Override
   public void describeTo(final Description description)
   {
      description.appendText("option that is part of specification ").appendValue(specification);
   }

   @Override
   protected boolean matchesSafely(final RawOption item)
   {
      if(!specification.isSpecified(item.stringValue())) {
         validationErrorBuilder.unexpectedOption(item.stringValue());
         return false;
      }
      return true;
   }
}
