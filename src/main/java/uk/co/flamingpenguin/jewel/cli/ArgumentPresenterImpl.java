package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
import static java.util.Arrays.asList;
import static uk.co.flamingpenguin.jewel.cli.ConvertTypeOfObject.converterTo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;

class ArgumentPresenterImpl<O> implements ArgumentPresenter<O> {
    private final OptionsSpecification<O> specification;
    private final ReflectedClass<O> klass;
    private final ArgumentPresentingStrategy<O> argumentPresentingStrategy;

    public ArgumentPresenterImpl(
            final ReflectedClass<O> klass,
            final OptionsSpecification<O> specification,
            final ArgumentPresentingStrategy<O> argumentPresentingStrategy) {
        this.specification = specification;
        this.klass = klass;
        this.argumentPresentingStrategy = argumentPresentingStrategy;
    }

    @Option public O presentArguments(final ArgumentCollection validatedArguments) throws ArgumentValidationException {
        final Map<String, Object> argumentMap = new LinkedHashMap<String, Object>();

        final ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilderImpl();

        final List<ReflectedMethod> optionMethods = klass.methods(annotatedWith(Option.class));
        for (final ReflectedMethod reflectedMethod : optionMethods) {
            final ParsedOptionSpecification optionSpecification =
                    specification.getSpecification(reflectedMethod);

            final ConvertTypeOfObject<?> convertTypeOfObject =
                    converterTo(validationErrorBuilder, optionSpecification, reflectedMethod);
            final Option optionAnnotation = reflectedMethod.annotation(Option.class);
            if (optionSpecification.isMultiValued())
            {
                argumentMap.put(
                        reflectedMethod.propertyName(),
                        convertTypeOfObject.convert(asList(optionAnnotation.defaultValue())));
            }
            else if (optionAnnotation.defaultValue() != null && optionAnnotation.defaultValue().length > 0)
            {
                argumentMap.put(
                        reflectedMethod.propertyName(),
                        convertTypeOfObject.convert(optionAnnotation.defaultValue()[0]));
            }

            final Argument argument = validatedArguments.getArgument(optionSpecification.getNames());
            if (argument != null) {
                if (optionSpecification.isMultiValued()) {
                    argumentMap.put(
                            optionSpecification.getCanonicalIdentifier(),
                            convertTypeOfObject.convert(argument.getValues()));
                } else if (!argument.getValues().isEmpty()) {
                    argumentMap.put(
                            optionSpecification.getCanonicalIdentifier(),
                            convertTypeOfObject.convert(argument.getValues().get(0)));
                } else if (argument.getValues().isEmpty()) {
                    argumentMap.put(optionSpecification.getCanonicalIdentifier(), null);
                }
            }
        }

        if (specification.hasUnparsedSpecification()) {
            final List<ReflectedMethod> unparsedMethods = klass.methods(annotatedWith(Unparsed.class));
            for (final ReflectedMethod reflectedMethod : unparsedMethods) {
                final ConvertTypeOfObject<?> convertTypeOfObject =
                        converterTo(
                                validationErrorBuilder,
                                specification.getUnparsedSpecification(),
                                reflectedMethod);
                if (!validatedArguments.getUnparsed().isEmpty())
                {
                    if (specification.getUnparsedSpecification().isMultiValued())
                    {
                        argumentMap.put(
                                reflectedMethod.propertyName(),
                                convertTypeOfObject.convert(validatedArguments.getUnparsed()));
                    }
                    else if (!validatedArguments.getUnparsed().isEmpty())
                    {
                        argumentMap.put(
                                reflectedMethod.propertyName(),
                                convertTypeOfObject.convert(validatedArguments.getUnparsed().get(0)));
                    }
                }
            }
        }
        validationErrorBuilder.validate();
        return argumentPresentingStrategy.presentArguments(argumentMap);
    }
}
