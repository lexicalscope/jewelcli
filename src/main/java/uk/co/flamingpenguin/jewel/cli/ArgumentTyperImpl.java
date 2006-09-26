package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ArgumentTyperImpl<O> implements ArgumentTyper<O>
{
   private final OptionsSpecification<O> m_specification;

   public ArgumentTyperImpl(final OptionsSpecification<O> specification)
   {
      m_specification = specification;
   }

   /**
    * @inheritdoc
    */
   public TypedArguments typeArguments(final ValidatedArguments validatedArguments) throws ArgumentValidationException
   {
      final ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilderImpl();
      final TypedArgumentsImpl typedArguments = new TypedArgumentsImpl();

      for(final OptionSpecification optionSpecification : m_specification)
      {
         try
         {
            if(validatedArguments.contains(optionSpecification.getShortName(), optionSpecification.getLongName()))
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
         catch (final NumberFormatException e)
         {
            validationErrorBuilder.invalidValueForType(optionSpecification, e.getMessage());
         }
         catch (final NoSuchMethodException e)
         {
            validationErrorBuilder.unableToConstructType(optionSpecification, e.getMessage());
         }
         catch (final InstantiationException e)
         {
            validationErrorBuilder.unableToConstructType(optionSpecification, e.getMessage());
         }
         catch (final IllegalAccessException e)
         {
            validationErrorBuilder.unableToConstructType(optionSpecification, e.getMessage());
         }
         catch (final InvocationTargetException e)
         {
            validationErrorBuilder.invalidValueForType(optionSpecification, e.getMessage());
         }
         catch (final ValueFormatException e)
         {
            validationErrorBuilder.invalidValueForType(optionSpecification, e.getMessage());
         }
      }
      validationErrorBuilder.validate();
      return typedArguments;
   }

   @SuppressWarnings("unchecked")
   private Object getValue(final ValidatedArguments arguments, final Method method, final OptionSpecification specification) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ValueFormatException
   {
      final List<String> values = arguments.getValues(specification.getShortName(), specification.getLongName());

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
               return value.charAt(0);
            }
            else if(type.equals(Byte.TYPE) || type.equals(Short.class))
            {
               return Byte.parseByte(value);
            }
            else if(type.equals(Short.TYPE) || type.equals(Short.class))
            {
               return Short.parseShort(value);
            }
            else if(type.equals(Integer.TYPE) || type.equals(Integer.class))
            {
               return Integer.parseInt(value);
            }
            else if(type.equals(Long.TYPE) || type.equals(Long.class))
            {
               return Long.parseLong(value);
            }
            else if(type.equals(Float.TYPE) || type.equals(Float.class))
            {
               return Float.parseFloat(value);
            }
            else if(type.equals(Double.TYPE) || type.equals(Double.class))
            {
               return Double.parseDouble(value);
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
               return value.charAt(0);
            }
            else
            {
               throw new ValueFormatException(String.format("value is not a charecter (%s)", value));
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
}
