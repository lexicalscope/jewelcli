package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

/**
 * The results of parsing a set of arguments according to the specification O
 *
 * @author Tim Wood
 *
 * @param <O> The interface that will be used to present the arguments
 */
public interface Arguments<O>
{
   /**
    * The parsed arguments
    *
    * @return The parsed arguments
    */
   O parsedArguments();

   /**
    * Any remaining unparsed arguments
    *
    * @return Any remaining unparsed arguments
    */
   List<String> unparsedArguments();
}
