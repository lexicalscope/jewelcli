package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;


class UnexpectedOptionSpecification implements OptionSpecification
{
   private final String m_name;

   public UnexpectedOptionSpecification(final String name)
   {
      m_name = name;
   }

   public String getLongName()
   {
      return m_name;
   }

   public String getName()
   {
      return m_name;
   }

   public String getShortName()
   {
      return "";
   }

   public Class<?> getType()
   {
      return Void.class;
   }

   public boolean hasShortName()
   {
      return false;
   }

   public boolean hasValue()
   {
      return false;
   }

   public boolean isMultiValued()
   {
      return false;
   }

   public boolean isOptional()
   {
      return false;
   }

   public String getDescription()
   {
      return String.format("Option not recognised");
   }

   public StringBuilder getSummary(final StringBuilder result)
   {
      return result.append(m_name);
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      getSummary(result).append(" : ").append(getDescription());
      return result.toString();
   }

   public Method getMethod()
   {
      return null;
   }
}
