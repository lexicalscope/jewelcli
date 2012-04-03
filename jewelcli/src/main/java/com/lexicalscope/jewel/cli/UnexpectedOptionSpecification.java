package com.lexicalscope.jewel.cli;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.jewel.cli.specification.OptionSpecification;
import com.lexicalscope.jewel.cli.specification.SpecificationMultiplicity;

class UnexpectedOptionSpecification implements OptionSpecification
{
    private final String m_name;

    UnexpectedOptionSpecification(final String name)
    {
        m_name = name;
    }

    @Override public String getCanonicalIdentifier() {
        return m_name;
    }

    @Override public Class<?> getType()
    {
        return Void.class;
    }

    @Override public boolean isMultiValued()
    {
        return false;
    }

    @Override public boolean isOptional()
    {
        return false;
    }

    @Override public ReflectedMethod getMethod() {
        return null;
    }

    @Override public ReflectedMethod getOptionalityMethod() {
        return null;
    }

    @Override public List<String> getDefaultValue() {
        return null;
    }

    @Override public boolean hasDefaultValue() {
        return false;
    }

    @Override public boolean isHidden() {
        return false;
    }

    @Override public String getDescription() {
        return "";
    }

    @Override public boolean allowedThisManyValues(final int count) {
        return false;
    }

    @Override public String toString()
    {
        final StringBuilder result = new StringBuilder();
        result.append(m_name);
        return result.toString();
    }

    @Override public boolean hasValue() {
        return false;
    }

    @Override public boolean hasExactCount() {
        return false;
    }

    @Override public int exactly() {
        return 0;
    }

    @Override public int minimum() {
        return 0;
    }

    @Override public int maximum() {
        return 0;
    }

    @Override public <T> T compareCountToSpecification(
            final int valueCount,
            final SpecificationMultiplicity<T> specificationMultiplicity) {
        return specificationMultiplicity.allowed();
    }

    @Override public int maximumArgumentConsumption() {
        return 0;
    }
}
