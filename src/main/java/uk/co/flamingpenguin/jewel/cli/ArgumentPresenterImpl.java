package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
import static com.lexicalscope.fluentreflection.bean.MapBean.bean;
import static java.util.Arrays.asList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ArgumentPresenterImpl<O> implements ArgumentPresenter<O> {
    private final OptionsSpecification<O> specification;
    private final Class<O> klass;

    public ArgumentPresenterImpl(final Class<O> klass, final OptionsSpecification<O> specification) {
        this.specification = specification;
        this.klass = klass;
    }

    /**
     * @inheritdoc
     */
    public O presentArguments(
            final TypedArguments arguments,
            final ArgumentCollection validatedArguments) {
        final ReflectedClass<O> reflectedKlass = type(klass);
        final Map<String, Object> argumentMap = new LinkedHashMap<String, Object>();

        final List<ReflectedMethod> optionMethods = reflectedKlass.methods(annotatedWith(Option.class));
        for (final ReflectedMethod reflectedMethod : optionMethods) {
            final Option optionAnnotation = reflectedMethod.annotation(Option.class);
            if (reflectedMethod.returnType().assignableTo(Iterable.class))
            {
                argumentMap.put(reflectedMethod.propertyName(), asList(optionAnnotation.defaultValue()));
            }
            else if (optionAnnotation.defaultValue() != null && optionAnnotation.defaultValue().length > 0)
            {
                argumentMap.put(reflectedMethod.propertyName(), optionAnnotation.defaultValue()[0]);
            }
        }

        for (final Argument argument : validatedArguments) {
            final OptionSpecification optionSpecification = specification.getSpecification(argument.getOptionName());
            if (optionSpecification.isMultiValued()) {
                argumentMap.put(optionSpecification.getLongName(), argument.getValues());
            } else if (!argument.getValues().isEmpty()) {
                argumentMap.put(optionSpecification.getLongName(), argument.getValues().get(0));
            } else if (argument.getValues().isEmpty()) {
                argumentMap.put(optionSpecification.getLongName(), null);
            }
        }

        if (specification.hasUnparsedSpecification()) {
            final List<ReflectedMethod> unparsedMethods = reflectedKlass.methods(annotatedWith(Unparsed.class));
            for (final ReflectedMethod reflectedMethod : unparsedMethods) {
                if (!validatedArguments.getUnparsed().isEmpty())
                {
                    if (reflectedMethod.returnType().assignableTo(Iterable.class))
                    {
                        argumentMap.put(reflectedMethod.propertyName(), validatedArguments.getUnparsed());
                    }
                    else if (!validatedArguments.getUnparsed().isEmpty())
                    {
                        argumentMap.put(reflectedMethod.propertyName(), validatedArguments.getUnparsed().get(0));
                    }
                }
            }
        }

        return bean(klass, argumentMap);
    }
}
