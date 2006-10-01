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
   private final boolean m_multiValued;
   private final Method m_method;

   public ArgumentSpecificationImpl(final Method method)
   {
      m_method = method;

      final Type returnType = method.getGenericReturnType();
      m_type = (Class<?>) (isList(method.getReturnType()) ? getListType(returnType) : returnType);
      m_multiValued = isList(method.getReturnType());
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
}