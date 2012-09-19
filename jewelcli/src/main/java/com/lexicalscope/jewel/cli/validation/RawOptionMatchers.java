//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RawOptionMatchers
{
   public static Matcher<RawOption> lastOption()
   {
      return new TypeSafeMatcher<RawOption>()
      {
         @Override
         public void describeTo(final Description description)
         {
            description.appendText("the last option");
         }

         @Override
         protected boolean matchesSafely(final RawOption item)
         {
            return item.isLast();
         }
      };
   }
}
