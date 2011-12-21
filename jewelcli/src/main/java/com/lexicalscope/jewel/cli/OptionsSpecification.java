package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

interface OptionsSpecification<O> extends CliSpecification {
    boolean isSpecified(String key);

    ParsedOptionSpecification getSpecification(String key);

    List<ParsedOptionSpecification> getMandatoryOptions();

    ParsedOptionSpecification getSpecification(ReflectedMethod reflectedMethod);

    void describeTo(HelpMessage helpMessage);
}