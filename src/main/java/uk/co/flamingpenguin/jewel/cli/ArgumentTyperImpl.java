package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ArgumentTyperImpl<O> implements ArgumentTyper<O>
{
   private final ValidationErrorBuilder m_validationErrorBuilder;
   private final OptionsSpecification<O> m_specification;

   public ArgumentTyperImpl(final OptionsSpecification<O> specification)
   {
      m_validationErrorBuilder = new ValidationErrorBuilderImpl();
      m_specification = specification;
   }

   /**
    * @inheritdoc
    */
   public TypedArguments typeArguments(final ArgumentCollection validatedArguments) throws ArgumentValidationException
   {
      final TypedArgumentsImpl typedArguments = typeParsedArguments(validatedArguments);
      typeUnparsedArguments(validatedArguments, typedArguments);
      m_validationErrorBuilder.validate();
      return typedArguments;
   }

   private void typeUnparsedArguments(final ArgumentCollection validatedArguments, final TypedArgumentsImpl typedArguments)
   {
      if(m_specification.hasUnparsedSpecification()
            && validatedArguments.hasUnparsed())
      {
         final ArgumentMethodSpecification specification = m_specification.getUnparsedSpecification();
         typedArguments.setUnparsedValue(getValue(specification.getMethod(), validatedArguments.getUnparsed(), specification));
      }
   }

   private TypedArgumentsImpl typeParsedArguments(final ArgumentCollection validatedArguments)
   {
      final TypedArgumentsImpl typedArguments = new TypedArgumentsImpl();

      for(final OptionMethodSpecification optionSpecification : m_specification)
      {
         if(validatedArguments.containsAny(optionSpecification.getAllNames()) || optionSpecification.hasDefaultValue())
         {
            final Object value;
            if(optionSpecification.hasValue())
            {
               value = getValue(validatedArguments, optionSpecification.getMethod(), optionSpecification);
            }
            else
            {
               value = Boolean.TRUE;
            }
            typedArguments.add(optionSpecification, value);
         }
      }
      return typedArguments;
   }

   private Object getValue(final ArgumentCollection arguments, final Method method, final OptionMethodSpecification specification)
   {
	  final List<String> values = arguments.containsAny(specification.getAllNames()) ? arguments.getValues(specification.getAllNames()) : specification.getDefaultValue();
      return getValue(method, values, specification);
   }

   @SuppressWarnings("unchecked")
   private Object getValue(final Method method, final List<String> values, final ArgumentMethodSpecification specification)
   {
      try
      {
         final Class<?> type = specification.getType();
         final List<Object> result = new ArrayList<Object>();

         for (final String value : values)
         {
            if(type.isEnum())
            {
               result.add(Enum.valueOf((Class<? extends Enum>) type, values.get(0)));
            }
            else if(type.isPrimitive())
            {
               if(type.equals(Character.TYPE) || type.equals(Character.class))
               {
                  result.add(value.charAt(0));
               }
               else if(type.equals(Byte.TYPE) || type.equals(Short.class))
               {
                  result.add(Byte.parseByte(value));
               }
               else if(type.equals(Short.TYPE) || type.equals(Short.class))
               {
                  result.add(Short.parseShort(value));
               }
               else if(type.equals(Integer.TYPE) || type.equals(Integer.class))
               {
                  result.add(Integer.parseInt(value));
               }
               else if(type.equals(Long.TYPE) || type.equals(Long.class))
               {
                  result.add(Long.parseLong(value));
               }
               else if(type.equals(Float.TYPE) || type.equals(Float.class))
               {
                  result.add(Float.parseFloat(value));
               }
               else if(type.equals(Double.TYPE) || type.equals(Double.class))
               {
                  result.add(Double.parseDouble(value));
               }
               else
               {
                  throw new UnsupportedOperationException(String.format("Method (%s) return type not supported for reading argument values", method.toGenericString()));
               }
            }
            else if(type.equals(Character.class))
            {
               // there is always an exception, and java.lang.Character can't constructed from a string
               if(value.length() == 1)
               {
                  result.add(value.charAt(0));
               }
               else
               {
                  throw new ValueFormatException(String.format("value is not a character (%s)", value));
               }
            }
            else
            {
               final Constructor<?> constructor = type.getConstructor(new Class[]{String.class});
               result.add(constructor.newInstance(new Object[]{value}));
            }
         }

         if(specification.isMultiValued())
         {
            return result;
         }
         else
         {
            return result.get(0);
         }
      }
      catch (final NumberFormatException e)
      {
         m_validationErrorBuilder.invalidValueForType(specification, unsupportedNumberFormatMessage(e));
      }
      catch (final NoSuchMethodException e)
      {
         m_validationErrorBuilder.unableToConstructType(specification, e.getMessage());
      }
      catch (final InstantiationException e)
      {
         m_validationErrorBuilder.unableToConstructType(specification, e.getMessage());
      }
      catch (final IllegalAccessException e)
      {
         m_validationErrorBuilder.unableToConstructType(specification, e.getMessage());
      }
      catch (final InvocationTargetException e)
      {
         final Throwable cause = e.getCause();
         if(cause instanceof NumberFormatException)
         {
            m_validationErrorBuilder.invalidValueForType(specification, unsupportedNumberFormatMessage((NumberFormatException) cause));
         }
         else
         {
            m_validationErrorBuilder.invalidValueForType(specification, cause.getMessage());
         }
      }
      catch (final ValueFormatException e)
      {
         m_validationErrorBuilder.invalidValueForType(specification, e.getMessage());
      }
      return null;
   }

   private String unsupportedNumberFormatMessage(final NumberFormatException e1)
   {
      return "Unsupported number format: " + e1.getMessage();
   }
}
