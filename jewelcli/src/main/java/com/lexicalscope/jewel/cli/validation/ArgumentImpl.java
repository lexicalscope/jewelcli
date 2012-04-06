//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import java.util.List;

import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

class ArgumentImpl implements Argument
{
    private final ParsedOptionSpecification m_optionName;
    private final List<String> m_values;

    ArgumentImpl(final ParsedOptionSpecification optionName, final List<String> values)
    {
        m_optionName = optionName;
        m_values = values;
    }

    /**
     * {@inheritDoc}
     */
    public ParsedOptionSpecification getOptionName()
    {
        return m_optionName;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getValues()
    {
        return m_values;
    }
}
