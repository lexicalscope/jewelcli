package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

class UnexpectedOptionSpecification implements OptionMethodSpecification
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

   public List<String> getShortNames()
   {
      return Collections.emptyList();
   }

   public List<String> getAllNames()
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

   public boolean patternMatches(final String value)
   {
      return false;
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

   /**
    * {@inheritdoc}
    */
   public Method getOptionalityMethod()
   {
      return null;
   }
}
