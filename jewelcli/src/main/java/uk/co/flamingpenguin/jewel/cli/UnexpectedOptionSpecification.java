package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

import com.lexicalscope.fluentreflection.ReflectedMethod;

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

    @Override public String toString()
    {
        final StringBuilder result = new StringBuilder();
        result.append(m_name).append(" : ").append("Option not recognised");
        return result.toString();
    }
}
