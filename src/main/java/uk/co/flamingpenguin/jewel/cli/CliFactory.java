package uk.co.flamingpenguin.jewel.cli;

/**
 * <p>Constructs a Cli from an annotated interface definition.</p>
 *
 * @see uk.co.flamingpenguin.jewel.cli.Option
 *
 * @author Tim Wood
 */
public abstract class CliFactory
{
   /**
    * Construct a Cli from an annotated interface definition
    *
    * @param <O> The type of the interface that will be used to present
    *            the argments
    * @param klass The annotated interface definition
    *
    * @return A Cli configured to create instance of klass
    */
   public static <O> Cli<O> createCli(final Class<O> klass)
   {
      return new CliImpl<O>(klass);
   }

   /**
    * Parse arguments from an annotated interface definition
    *
    * @param <O> The type of the interface that will be used to present
    *            the argments
    * @param klass The annotated interface definition
    * @param arguments
    *
    * @return The parsed arguments
    *
    * @throws InvalidArgumentsException
    * @throws ArgumentValidationException
    */
   public static <O> O parseArguments(final Class<O> klass, final String... arguments) throws ArgumentValidationException
   {
      return createCli(klass).parseArguments(arguments);
   }
}
