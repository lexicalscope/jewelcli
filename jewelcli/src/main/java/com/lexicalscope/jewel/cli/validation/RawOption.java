//
//Author       : t.wood
//Copyright    : (c) Resilient Networks plc 2012 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

public class RawOption
{
   private final String optionName;
   private final boolean last;

   public RawOption(final String optionName, final boolean last)
   {
      this.optionName = optionName;
      this.last = last;
   }

   public RawOption(final String optionName)
   {
      this(optionName, false);
   }

   public String stringValue()
   {
      return optionName;
   }

   public boolean isLast()
   {
      return last;
   }
}
