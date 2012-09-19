//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.specification;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OptionSpecificationMatchers
{
   public static Matcher<ParsedOptionSpecification> mandatory()
   {
      return new TypeSafeMatcher<ParsedOptionSpecification>(ParsedOptionSpecification.class)
      {
         @Override
         public void describeTo(final Description description)
         {
            description.appendText("mandatory option");
         }

         @Override
         protected boolean matchesSafely(final ParsedOptionSpecification item)
         {
            return !item.isOptional();
         }
      };
   }
}
