package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            new InvocationHandler(){
               public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
               {
                  if(args != null && args.length != 0)
                  {
                     throw new UnsupportedOperationException(String.format("Method (%s) with arguments not supported for reading argument values", method.toGenericString()));
                  }
                  else if(method.isAnnotationPresent(Unparsed.class))
                  {
                     return arguments.getUnparsedValue();
                  }
                  else if(m_specification.hasUnparsedSpecification() && 
                          m_specification.getUnparsedSpecification().isOptional() && 
                          m_specification.getUnparsedSpecification().getOptionalityMethod().equals(method))
                  {
                     return arguments.hasUnparsedValue();
                  }
                  else if(!m_specification.isSpecified(method))
                  {
                     throw new UnsupportedOperationException(String.format("Method (%s) is not annotated for option specification", method.toGenericString()));
                  }

                  final OptionSpecification specification = m_specification.getSpecification(method);

                  if(m_specification.isExistenceChecker(method) || !specification.hasValue())
                  {
                     return optionPresent(arguments, specification);
                  }
                  return getValue(arguments, method, specification);
               }

               private Object optionPresent(final TypedArguments arguments, final ArgumentSpecification specification)
               {
                  return arguments.contains(specification);
               }

               private Object getValue(final TypedArguments arguments, final Method method, final ArgumentSpecification specification) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
               {
                  final Object value = arguments.getValue(specification);
                  if(value == null)
                  {
                     throw new OptionNotPresentException(specification);
                  }
                  return value;
               }});
      return result;
   }
}
