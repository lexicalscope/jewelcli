//
//Author       : t.wood
//Copyright    : (c) Teamphone.com Ltd. 2008 - All Rights Reserved
//
package com.lexicalscope.jewel.cli;

import java.util.List;

/**
 * A parsed argument
 *
 * @author t.wood
 */
interface Argument
{
   /**
    * The option name
    *
    * @return the option name
    */
   String getOptionName();

   /**
    * The values
    *
    * @return The values
    */
   List<String> getValues();
}
