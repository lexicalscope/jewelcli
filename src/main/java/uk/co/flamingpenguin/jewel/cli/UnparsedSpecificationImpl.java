package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

class UnparsedSpecificationImpl extends ArgumentSpecificationImpl implements ArgumentSpecification
{
   private final String m_name;

   public UnparsedSpecificationImpl(final Method method, final Class<?> klass)
   {
      super(method);
      final Unparsed annotation = method.getAnnotation(Unparsed.class);
      m_name = annotation.name();
   }

   public final String getName()
   {
      return m_name;
   }
}
