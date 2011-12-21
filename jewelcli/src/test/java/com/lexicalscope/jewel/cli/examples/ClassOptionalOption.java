package com.lexicalscope.jewel.cli.examples;

import com.lexicalscope.jewel.cli.Option;

public class ClassOptionalOption
{
    private Integer myOptionalOption;
    private Integer myMandatoryOption;

    Integer getMyOptionalOption()
    {
        return myOptionalOption;
    }

    @Option(defaultToNull = true) void setMyOptionalOption(final Integer myOptionalOption) {
        this.myOptionalOption = myOptionalOption;
    }

    Integer getMyMandatoryOption()
    {
        return myMandatoryOption;
    }

    @Option void setMyMandatoryOption(final Integer myMandatoryOption) {
        this.myMandatoryOption = myMandatoryOption;
    }
}
