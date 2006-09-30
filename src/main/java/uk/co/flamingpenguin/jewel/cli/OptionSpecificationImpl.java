package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;


class OptionSpecificationImpl implements OptionSpecification
{
   private static final Logger g_logger = Logger.getLogger(OptionSpecificationImpl.class.getName());
   private final String m_name;
   private final String m_shortName;
   private final String m_longName;
   private final Class<?> m_type;
   private final boolean m_multiValued;
   private final Method m_optionalityValue;
   private final String m_description;
   private final Method m_method;
   private final String m_pattern;

   public OptionSpecificationImpl(final Method method, final Class<?> klass)
   {
      final Class<?> returnType = method.getReturnType();

      m_type = isList(returnType) ? getListType(method) : returnType;
      m_multiValued = isList(returnType);

      final Option optionAnnotation = method.getAnnotation(Option.class);

      m_name = extractOptionName(method.getName());

      final String shortNameSpecification = optionAnnotation.shortName().trim();
      m_shortName = shortNameSpecification.length() > 0 ? shortNameSpecification.substring(0, 1) : "";

      final String longNameSpecification = optionAnnotation.longName().trim();
      m_longName = nullOrBlank(longNameSpecification) ? m_name : longNameSpecification;

      m_method = method;

      m_optionalityValue = getOptionalityMethod(m_name, klass);

      m_description = optionAnnotation.description().trim();

      m_pattern = optionAnnotation.pattern();

      g_logger.finer(String.format("Create option specification name:%s, shortName:%s, type:%s (multiValued:%b, hasValue:%b, isOptional:%b)) ",
                                  getName(), getShortName(), getType(), isMultiValued(), hasValue(), isOptional()));
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
   public Class<?> getType()
   {
      return m_type;
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
   public String getShortName()
   {
      return m_shortName;
   }

   /**
    * @inheritdoc
    */
   public String getLongName()
   {
      return m_longName;
   }

   /**
    * @inheritdoc
    */
   public boolean isMultiValued()
   {
      return m_multiValued;
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
      return !isBoolean(m_type) ;
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
      return !nullOrBlank(m_shortName);
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

   private boolean isList(final Class<?> klass)
   {
      return klass.isAssignableFrom(List.class);
   }

   private Class<?> getListType(final Method method)
   {
      final Type genericReturnType = method.getGenericReturnType();
      if(genericReturnType instanceof ParameterizedType)
      {
         return (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
      }
      else
      {
         g_logger.finer("Found raw List type; assuming List<String>.");
         return String.class;
      }
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

      if(hasShortName())
      {
         result.append(" -").append(getShortName());
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

   public Method getMethod()
   {
      return m_method;
   }
}
