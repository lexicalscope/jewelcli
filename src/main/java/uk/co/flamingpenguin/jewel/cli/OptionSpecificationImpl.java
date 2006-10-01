package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


class OptionSpecificationImpl extends ArgumentSpecificationImpl implements OptionSpecification
{
   private static final Logger g_logger = Logger.getLogger(OptionSpecificationImpl.class.getName());
   private final String m_name;
   private final List<String> m_allNames = new ArrayList<String>();
   private final List<String> m_shortNames;
   private final String m_longName;
   private final Method m_optionalityValue;
   private final String m_description;
   private final String m_pattern;

   public OptionSpecificationImpl(final Method method, final Class<?> klass)
   {
      super(method);

      final Option optionAnnotation = method.getAnnotation(Option.class);

      m_name = extractOptionName(method.getName());

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
      m_longName = nullOrBlank(longNameSpecification) ? m_name : longNameSpecification;
      m_allNames.add(m_longName);

      m_optionalityValue = getOptionalityMethod(m_name, klass);

      m_description = optionAnnotation.description().trim();

      m_pattern = optionAnnotation.pattern();

      g_logger.finer(String.format("Create option specification name:%s, shortName:%s, type:%s (multiValued:%b, hasValue:%b, isOptional:%b)) ",
                                  getName(), getShortNames(), getType(), isMultiValued(), hasValue(), isOptional()));
   }

   private Method getOptionalityMethod(final String name, Class<?> klass)
   {
      try
      {
         final Method method = klass.getMethod(addPrefix("is", name), new Class[]{});
         if(isBoolean(method.getReturnType()))
         {
            return method;
         }
         return null;
      }
      catch (final NoSuchMethodException e)
      {
         return null;
      }
   }

   public Method getOptionalityMethod()
   {
      return m_optionalityValue;
   }

   /**
    * @inheritdoc
    */
   public String getName()
   {
      return m_name;
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
   public boolean hasValue()
   {
      return !isBoolean(getType());
   }

   private boolean isBoolean(final Class<?> type)
   {
      return (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class));
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

   private String extractOptionName(final String methodName)
   {
      final String isPrefix = "is";
      if(!hasValue() && methodName.startsWith(isPrefix))
      {
         return stripPrefix(methodName, isPrefix);
      }
      return stripPrefix(methodName, "get");
   }

   private String stripPrefix(final String methodName, final String prefix)
   {
      if(methodName.length() > prefix.length() && methodName.startsWith(prefix))
      {
         return methodName.substring(prefix.length(), prefix.length() + 1).toLowerCase()
                + ((methodName.length() > prefix.length()+1) ? methodName.substring(prefix.length() + 1) : "");
      }
      return methodName;
   }

   private String addPrefix(final String prefix, final String name)
   {
      return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
   }

   /**
    * @inheritdoc
    */
   public boolean isOptional()
   {
      return m_optionalityValue != null || !hasValue();
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
}
