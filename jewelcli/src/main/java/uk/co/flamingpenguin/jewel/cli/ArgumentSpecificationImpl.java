package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

class ArgumentSpecificationImpl
{
   private static final Logger g_logger = Logger.getLogger(ArgumentSpecificationImpl.class.getName());

   private final Class<?> m_type;
   private final Method m_method;
   private final boolean m_multiValued;

   private final String m_baseName;
   private final Method m_optionalityMethod;

   public ArgumentSpecificationImpl(final Method method, final Class<?> klass)
   {
      m_method = method;

      final Type returnType = method.getGenericReturnType();
      m_type = (Class<?>) (isList(method.getReturnType()) ? getListType(returnType) : returnType);
      m_multiValued = isList(method.getReturnType());

      m_baseName = extractBaseMethodName(method);
      m_optionalityMethod = findCorrespondingOptionalityMethod(m_baseName, klass);
   }

   /**
    * @inheritdoc
    */
   public final String getName()
   {
      return m_baseName;
   }

   /**
    * {@inheritDoc}
    */
   public final Class<?> getType()
   {
      return m_type;
   }

   /**
    * {@inheritDoc}
    */
   public final boolean isMultiValued()
   {
      return m_multiValued;
   }

   /**
    * {@inheritDoc}
    */
   public final Method getMethod()
   {
      return m_method;
   }

   /**
    * @inheritdoc
    */
   public boolean isOptional()
   {
      return m_optionalityMethod != null || !hasValue();
   }

   public Method getOptionalityMethod()
   {
      return m_optionalityMethod;
   }

   /**
    * @inheritdoc
    */
   public boolean hasValue()
   {
      return !isBoolean(getType());
   }

   private final boolean isList(final Class<?> klass)
   {
      return klass.isAssignableFrom(List.class);
   }

   private final Class<?> getListType(final Type genericReturnType)
   {
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

   private final Method findCorrespondingOptionalityMethod(final String name, Class<?> klass)
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

   private final boolean isBoolean(final Class<?> type)
   {
      return (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class));
   }

   private final String stripPrefix(final String methodName, final String prefix)
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

   private String extractBaseMethodName(final Method method)
   {
      final String methodName = method.getName();

      final String isPrefix = "is";
      if(isBoolean(method.getReturnType()) && methodName.startsWith(isPrefix))
      {
         return stripPrefix(methodName, isPrefix);
      }
      return stripPrefix(methodName, "get");
   }
}