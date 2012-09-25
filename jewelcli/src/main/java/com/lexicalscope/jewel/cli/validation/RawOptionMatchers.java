//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import java.util.List;
import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RawOptionMatchers
{
   public static Matcher<Entry<RawOption, List<String>>> isLastOption()
   {
      return new TypeSafeMatcher<Entry<RawOption, List<String>>>()
      {
         @Override
         public void describeTo(final Description description)
         {
            description.appendText("the last option");
         }

         @Override
         protected boolean matchesSafely(final Entry<RawOption, List<String>> item)
         {
            return item.getKey().isLast();
         }
      };
   }
}
