package com.lexicalscope.jewel.cli;

import static com.lexicalscope.jewel.cli.ConvertTypeOfObject.converterTo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class ArgumentPresenterImpl<O> implements ArgumentPresenter<O> {
    private final OptionsSpecification<O> specification;
    private final ArgumentPresentingStrategy<O> argumentPresentingStrategy;

    public ArgumentPresenterImpl(
            final OptionsSpecification<O> specification,
            final ArgumentPresentingStrategy<O> argumentPresentingStrategy) {
        this.specification = specification;
        this.argumentPresentingStrategy = argumentPresentingStrategy;
    }

    @Option public O presentArguments(final ArgumentCollection validatedArguments) throws ArgumentValidationException {
        final Map<String, Object> argumentMap = new LinkedHashMap<String, Object>();

        final ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilderImpl();

        for (final ParsedOptionSpecification optionSpecification : specification) {
            final ConvertTypeOfObject<?> convertTypeOfObject =
                    converterTo(validationErrorBuilder, optionSpecification, optionSpecification.getMethod());
            putDefaultInMap(argumentMap, optionSpecification, convertTypeOfObject);

            final Argument argument = validatedArguments.getArgument(optionSpecification.getNames());
            if (argument != null) {
                putValuesInMap(argumentMap, optionSpecification, convertTypeOfObject, argument.getValues());
            }
        }

        if (specification.hasUnparsedSpecification()) {
            final UnparsedOptionSpecification unparsedSpecification = specification.getUnparsedSpecification();

            final ConvertTypeOfObject<?> convertTypeOfObject =
                    converterTo(
                            validationErrorBuilder,
                            unparsedSpecification,
                            unparsedSpecification.getMethod());

            putDefaultInMap(argumentMap, unparsedSpecification, convertTypeOfObject);
            if (!validatedArguments.getUnparsed().isEmpty()) {
                putValuesInMap(
                        argumentMap,
                        unparsedSpecification,
                        convertTypeOfObject,
                        validatedArguments.getUnparsed());
            }
        }
        validationErrorBuilder.validate();
        return argumentPresentingStrategy.presentArguments(argumentMap);
    }

    private void putValuesInMap(
            final Map<String, Object> argumentMap,
            final OptionSpecification specification,
            final ConvertTypeOfObject<?> convertTypeOfObject,
            final List<String> values) {
        if (specification.isMultiValued())
        {
            argumentMap.put(
                    specification.getCanonicalIdentifier(),
                    convertTypeOfObject.convert(values));
        }
        else if (!values.isEmpty())
        {
            argumentMap.put(
                    specification.getCanonicalIdentifier(),
                    convertTypeOfObject.convert(values.get(0)));
        } else if (values.isEmpty()) {
            argumentMap.put(specification.getCanonicalIdentifier(), null);
        }
    }

    private void putDefaultInMap(
            final Map<String, Object> argumentMap,
            final OptionSpecification optionSpecification,
            final ConvertTypeOfObject<?> convertTypeOfObject) {
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
    }
}
