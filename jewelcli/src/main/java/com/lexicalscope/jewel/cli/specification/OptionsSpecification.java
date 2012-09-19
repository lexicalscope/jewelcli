package com.lexicalscope.jewel.cli.specification;

import com.lexicalscope.fluent.list.FluentList;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.jewel.cli.HelpMessage;

/**
 * BETA: unstable may change in future versions
 *
 * @author tim
 *
 * @param <O>
 */
public interface OptionsSpecification<O> extends CliSpecification {
    boolean isSpecified(String key);

    ParsedOptionSpecification getSpecification(String key);

    FluentList<ParsedOptionSpecification> getMandatoryOptions();

    ParsedOptionSpecification getSpecification(ReflectedMethod reflectedMethod);

    void describeTo(HelpMessage helpMessage);
}