package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


class OptionSpecificationImpl extends ArgumentSpecificationImpl implements OptionSpecification
{
   private static final Logger g_logger = Logger.getLogger(OptionSpecificationImpl.class.getName());
   private final List<String> m_allNames = new ArrayList<String>();
   private final List<String> m_shortNames;
   private final String m_longName;
   private final String m_description;
   private final String m_pattern;
   private final List<String> m_default;

   public OptionSpecificationImpl(final Method method, final Class<?> klass)
   {
      super(method, klass);

      final Option optionAnnotation = method.getAnnotation(Option.class);

      final String[] shortNameSpecification = optionAnnotation.shortName();
      m_shortNames = new ArrayList<String>();
      for (int i = 0; i < shortNameSpecification.length; i++)
      {
         final String shortName = shortNameSpecification[i].trim();
         if(shortName.length() > 0)
         {
            m_shortNames.add(shortNameSpecification[i].substring(0, 1));
         }
      }
      m_allNames.addAll(m_shortNames);

      final String longNameSpecification = optionAnnotation.longName().trim();
      m_longName = nullOrBlank(longNameSpecification) ? getName() : longNameSpecification;
      m_allNames.add(m_longName);

      m_description = optionAnnotation.description().trim();

      m_pattern = optionAnnotation.pattern();
      m_default = Arrays.asList(optionAnnotation.defaultValue());

      g_logger.finer(String.format("Create option specification name:%s, shortName:%s, type:%s (multiValued:%b, hasValue:%b, isOptional:%b, defaultValue:%s)) ",
                                  getName(), getShortNames(), getType(), isMultiValued(), hasValue(), isOptional(), getDefaultValue()));
   }

   /**
    * @inheritdoc
    */
   public List<String> getShortNames()
   {
      return m_shortNames;
   }

   /**
    * @inheritdoc
    */
   public String getLongName()
   {
      return m_longName;
   }

   public boolean hasCustomPattern()
   {
      return !m_pattern.equals(".*");
   }

   public boolean patternMatches(final String value)
   {
      return value.matches(m_pattern);
   }


   /**
    * @inheritdoc
    */
   public boolean hasShortName()
   {
      return m_shortNames.size() > 0;
   }

   private boolean nullOrBlank(final String string)
   {
      return string == null || string.equals("");
   }



   /**
    * @inheritdoc
    */
   public String getDescription()
   {
      return m_description;
   }

   public StringBuilder getSummary(final StringBuilder result)
   {
      if(isOptional())
      {
         result.append("[");
      }

      result.append("--").append(getLongName());

      for (final String shortName : getShortNames())
      {
         result.append(" -").append(shortName);
      }

      if(hasValue())
      {
         if(hasCustomPattern())
         {
            result.append(" /").append(m_pattern).append("/");
         }
         else
         {
            result.append(" value");
         }
         if(isMultiValued())
         {
            result.append("...");
         }
      }

      if(isOptional())
      {
         result.append("]");
      }

      return result;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();

      getSummary(result);

      if(!nullOrBlank(getDescription()))
      {
         result.append(" : ").append(getDescription());
      }

      return result.toString();
   }

   public List<String> getAllNames()
   {
      return m_allNames;
   }

   public List<String> getDefaultValue()
   {
		return m_default;
   }

   public boolean hasDefaultValue()
   {
	   return m_default.size() > 0;
   }
}
