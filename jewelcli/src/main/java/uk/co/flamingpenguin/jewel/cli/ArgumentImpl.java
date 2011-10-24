//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

class ArgumentImpl implements Argument
{
    private final String m_optionName;
    private final List<String> m_values;

    ArgumentImpl(final String optionName, final List<String> values)
    {
        m_optionName = optionName;
        m_values = values;
    }

    /**
     * {@inheritDoc}
     */
    public String getOptionName()
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
