package uk.co.flamingpenguin.jewel.cli;

import static uk.co.flamingpenguin.jewel.cli.ConvertTypeOfObject.converterTo;

import java.util.LinkedHashMap;
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
            final UnparsedOptionSpecification unparsedSpecification = specification.getUnparsedSpecification();

            final ConvertTypeOfObject<?> convertTypeOfObject =
                    converterTo(
                            validationErrorBuilder,
                            unparsedSpecification,
                            unparsedSpecification.getMethod());

            putDefaultInMap(argumentMap, unparsedSpecification, convertTypeOfObject);

            if (!validatedArguments.getUnparsed().isEmpty())
            {
                if (unparsedSpecification.isMultiValued())
                {
                    argumentMap.put(
                            unparsedSpecification.getCanonicalIdentifier(),
                            convertTypeOfObject.convert(validatedArguments.getUnparsed()));
                }
                else if (!validatedArguments.getUnparsed().isEmpty())
                {
                    argumentMap.put(
                            unparsedSpecification.getCanonicalIdentifier(),
                            convertTypeOfObject.convert(validatedArguments.getUnparsed().get(0)));
                }
            }
        }
        validationErrorBuilder.validate();
        return argumentPresentingStrategy.presentArguments(argumentMap);
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
