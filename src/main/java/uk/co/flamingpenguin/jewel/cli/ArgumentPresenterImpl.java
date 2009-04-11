package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Proxy;

class ArgumentPresenterImpl<O> implements ArgumentPresenter<O>
{
   private final OptionsSpecification<O> m_specification;
   private final Class<O> m_klass;

   public ArgumentPresenterImpl(final Class<O> klass, final OptionsSpecification<O> specification)
   {
      m_specification = specification;
      m_klass = klass;
   }

   /**
    * @inheritdoc
    */
   @SuppressWarnings("unchecked")
   public O presentArguments(final TypedArguments arguments)
   {
      final O result = (O) Proxy.newProxyInstance(m_klass.getClassLoader(),
            new Class[] { m_klass },
            new ArgumentInvocationHandler(m_klass, m_specification, arguments));
      return result;
   }
}
