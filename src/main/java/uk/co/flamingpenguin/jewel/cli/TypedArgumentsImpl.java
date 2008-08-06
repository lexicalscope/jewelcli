package uk.co.flamingpenguin.jewel.cli;

import java.util.HashMap;

class TypedArgumentsImpl implements TypedArguments
{
   private final HashMap<OptionMethodSpecification, Object> m_values = new HashMap<OptionMethodSpecification, Object>();
   private Object m_unparsedValue;

   public void add(final OptionMethodSpecification optionSpecification, final Object value)
   {
      m_values.put(optionSpecification, value);
   }

   public boolean contains(final ArgumentMethodSpecification optionSpecification)
   {
      return m_values.containsKey(optionSpecification);
   }

   public Object getValue(final ArgumentMethodSpecification optionSpecification)
   {
      return m_values.get(optionSpecification);
   }

   public void setUnparsedValue(final Object unparsedValue)
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
