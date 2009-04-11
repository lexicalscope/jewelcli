/*
 * Copyright 2007 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright 2009 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

final class ArgumentInvocationHandler<O> implements InvocationHandler
{
   private final OptionsSpecification<O> m_specification;
   private final Class<O> m_klass;
   private final TypedArguments m_arguments;

   ArgumentInvocationHandler(final Class<O> klass,
                             final OptionsSpecification<O> specification,
                             final TypedArguments arguments)
   {
      m_klass = klass;
      m_specification = specification;
      m_arguments = arguments;
   }

   /**
    * {@inheritdoc}
    */
   public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
   {
      if(args == null)
      {
         if(method.getName().equals("hashCode"))
         {
            return this.hashCode();
         }
         else if(method.getName().equals("toString"))
         {
            return m_klass.getName();
         }
      }

      if(args != null && args.length == 1 && method.getName().equals("equals"))
      {
         final Object that = args[0];
         return Proxy.isProxyClass(that.getClass()) && Proxy.getInvocationHandler(that).equals(this);
      }
      if(args != null && args.length != 0)
      {
         throw new UnsupportedOperationException(String.format("Method (%s) with arguments not supported for reading argument values", method.toGenericString()));
      }
      else if(method.isAnnotationPresent(Unparsed.class))
      {
         return m_arguments.getUnparsedValue();
      }
      else if(m_specification.hasUnparsedSpecification() &&
              m_specification.getUnparsedSpecification().isOptional() &&
              m_specification.getUnparsedSpecification().getOptionalityMethod().equals(method))
      {
         return m_arguments.hasUnparsedValue();
      }
      else if(!m_specification.isSpecified(method))
      {
         throw new UnsupportedOperationException(String.format("Method (%s) is not annotated for option specification", method.toGenericString()));
      }

      final OptionMethodSpecification specification = m_specification.getSpecification(method);

      if(m_specification.isExistenceChecker(method) || !specification.hasValue())
      {
         return optionPresent(m_arguments, specification);
      }
      return getValue(m_arguments, method, specification);
   }

   private Object optionPresent(final TypedArguments arguments, final ArgumentMethodSpecification specification)
   {
      return arguments.contains(specification);
   }

   private Object getValue(final TypedArguments arguments, final Method method, final ArgumentMethodSpecification specification) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
   {
      final Object value = arguments.getValue(specification);
      if(value == null)
      {
         throw new OptionNotPresentException(specification);
      }
      return value;
   }
}