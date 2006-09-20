package uk.co.flamingpenguin.jewel.cli;

/**
 * <p>Constructs a Cli from an annotated interface definition.</p> 
 * 
 * @see uk.co.flamingpenguin.jewel.cli.Option
 * 
 * @author Tim Wood
 */
public class CliFactory
{
   /**
    * Construct a Cli from an annotated interface definition
    * 
    * @param <O> The type of the interface that will be used to present
    *            the argments.
    * @param klass The annotated interface definition
    * 
    * @return A Cli configured to create instance of klass
    */
   public static <O> Cli<O> createCli(final Class<O> klass)
   {
      return new CliImpl<O>(klass);
   }
}
