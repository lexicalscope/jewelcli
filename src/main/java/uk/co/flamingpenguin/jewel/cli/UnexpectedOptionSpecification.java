package uk.co.flamingpenguin.jewel.cli;

import java.util.Collections;
import java.util.List;

class UnexpectedOptionSpecification implements OptionSpecification
{
   private final String m_name;

   UnexpectedOptionSpecification(final String name)
   {
      m_name = name;
   }

   public String getLongName()
   {
      return m_name;
   }

   public List<String> getShortNames()
   {
      return Collections.emptyList();
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

   public List<String> getDefaultValue()
   {
      return Collections.emptyList();
   }

   public boolean hasDefaultValue()
   {
      return false;
   }

   public boolean isHelpOption()
   {
      return false;
   }

   public String getPattern()
   {
      return ".*";
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(m_name).append(" : ").append(getDescription());
      return result.toString();
   }
}
