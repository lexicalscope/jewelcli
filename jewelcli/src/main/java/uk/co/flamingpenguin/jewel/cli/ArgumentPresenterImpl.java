package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.annotatedWith;
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

        for (final ParsedOptionSpecification optionSpecification : specification) {
            final ConvertTypeOfObject<?> convertTypeOfObject =
                    converterTo(validationErrorBuilder, optionSpecification, optionSpecification.getMethod());
            if (optionSpecification.isMultiValued() && optionSpecification.hasDefaultValue())
            {
                argumentMap.put(
                        optionSpecification.getCanonicalIdentifier(),
                        convertTypeOfObject.convert(optionSpecification.getDefaultValue()));
            }
            else if (optionSpecification.hasDefaultValue() && optionSpecification.getDefaultValue().size() > 0)
            {
                argumentMap.put(
                        optionSpecification.getCanonicalIdentifier(),
                        convertTypeOfObject.convert(optionSpecification.getDefaultValue().get(0)));
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
