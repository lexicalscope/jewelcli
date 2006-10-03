package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

class UnparsedSpecificationImpl extends ArgumentSpecificationImpl implements ArgumentSpecification
{
   private final String m_longName;

   public UnparsedSpecificationImpl(final Method method, final Class<?> klass)
   {
      super(method, klass);
      final Unparsed annotation = method.getAnnotation(Unparsed.class);
      m_longName = annotation.name();
   }

   public final String getLongName()
   {
      return m_longName;
   }
}
