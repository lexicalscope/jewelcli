//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package com.lexicalscope.jewel.cli.validation;

import java.util.List;

import com.lexicalscope.jewel.cli.specification.ParsedOptionSpecification;

/**
 * A parsed argument
 *
 * @author t.wood
 */
public interface Argument
{
   /**
    * The option name
    *
    * @return the option name
    */
   ParsedOptionSpecification getOptionName();

   /**
    * The values
    *
    * @return The values
    */
   List<String> getValues();
}
