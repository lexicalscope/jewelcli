package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

interface OptionsSpecification<O> extends CliSpecification {
    boolean isSpecified(String key);

    OptionSpecification getSpecification(String key);

    List<OptionSpecification> getMandatoryOptions();

    OptionSpecification getSpecification(ReflectedMethod reflectedMethod);
}