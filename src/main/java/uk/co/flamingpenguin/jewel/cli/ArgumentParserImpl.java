/*
 * Copyright 2006 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.flamingpenguin.jewel.cli;


class ArgumentParserImpl implements ArgumentParser
{
   private final String[] arguments;

   public ArgumentParserImpl(final String ... arguments)
   {
      this.arguments = arguments;
   }

   /**
    * @inheritdoc
    */
   public ParsedArguments parseArguments() throws ArgumentValidationException
   {
      final ParsedArgumentsBuilder builder = new ParsedArgumentsBuilder();

      for (int i = 0; i < arguments.length; i++)
      {
         if(arguments[i].equals("--") && arguments.length > i + 2)
         {
            String[] unparsed = new String[arguments.length - i - 1];
            System.arraycopy(arguments, i + 1, unparsed, 0, arguments.length - i - 1);
            builder.setUnparsed(unparsed);

            break;
         }
         builder.add(arguments[i]);
      }
      return builder.getParsedArguments();
   }
}
