package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

interface OptionsSpecification<O> extends CliSpecification {
    boolean isSpecified(final String key);

    boolean isSpecified(Method method);

    boolean isExistenceChecker(ReflectedMethod method);

    boolean isUnparsedExistenceChecker(ReflectedMethod method);

    OptionSpecification getSpecification(final String key);

    OptionSpecification getSpecification(final Method key);

    List<OptionSpecification> getMandatoryOptions();

    boolean isUnparsedMethod(Method method);

    OptionSpecification getSpecification(ReflectedMethod reflectedMethod);
}