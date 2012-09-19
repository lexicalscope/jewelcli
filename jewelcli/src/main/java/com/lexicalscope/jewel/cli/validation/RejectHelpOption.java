//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import com.lexicalscope.jewel.cli.HelpRequestedException;
import com.lexicalscope.jewel.cli.specification.OptionsSpecification;
import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RejectHelpOption extends TypeSafeMatcher<ParsedOptionSpecification>
{
   private final OptionsSpecification<?> specification;

   public RejectHelpOption(final OptionsSpecification<?> specification)
   {
      this.specification = specification;
   }

   @Override
   public void describeTo(final Description description)
   {
      description.appendText("non help option");
   }

   @Override
   protected boolean matchesSafely(final ParsedOptionSpecification item)
   {
      if(item.isHelpOption())
      {
         throw new HelpRequestedException(specification);
      }
      return true;
   }
}
