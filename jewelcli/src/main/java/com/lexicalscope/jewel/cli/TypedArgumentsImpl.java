package com.lexicalscope.jewel.cli;

import java.util.HashMap;
import java.util.Map;

import com.lexicalscope.jewel.cli.specification.OptionSpecification;

class TypedArgumentsImpl implements TypedArguments
{
   private final Map<OptionSpecification, Object> m_values = new HashMap<OptionSpecification, Object>();
   private Object m_unparsedValue;

   void add(final OptionSpecification optionSpecification, final Object value)
   {
      m_values.put(optionSpecification, value);
   }

   public boolean contains(final OptionSpecification optionSpecification)
   {
      return m_values.containsKey(optionSpecification);
   }

   public Object getValue(final OptionSpecification optionSpecification)
   {
      return m_values.get(optionSpecification);
   }

   void setUnparsedValue(final Object unparsedValue)
   {
      m_unparsedValue = unparsedValue;
   }

   public Object getUnparsedValue()
   {
      return m_unparsedValue;
   }

   public boolean hasUnparsedValue()
   {
      return m_unparsedValue != null;
   }
}
