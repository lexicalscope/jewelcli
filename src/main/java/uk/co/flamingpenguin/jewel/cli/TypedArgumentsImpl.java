package uk.co.flamingpenguin.jewel.cli;

import java.util.HashMap;

class TypedArgumentsImpl implements TypedArguments
{
   private final HashMap<OptionSpecification, Object> m_values = new HashMap<OptionSpecification, Object>();

   public void add(final OptionSpecification optionSpecification, final Object value)
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
}
