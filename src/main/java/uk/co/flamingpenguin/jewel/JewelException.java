package uk.co.flamingpenguin.jewel;

/**
 * Superclass of all Jewel Exceptions
 *
 * @author Tim Wood
 */
public class JewelException extends Exception
{
   private static final long serialVersionUID = 5015614550344133699L;

   /**
    * A new exception with no message
    */
   public JewelException()
   {
      super();
   }

   /**
    * A new exception with the given message and cause
    *
    * @param message The message
    * @param cause The cause
    */
   public JewelException(final String message, final Throwable cause)
   {
      super(message, cause);
   }

   /**
    * A new exception with the given message
    *
    * @param message The message
    */
   public JewelException(final String message)
   {
      super(message);
   }

   /**
    * A new exception with the given cause
    *
    * @param cause The cause
    */
   public JewelException(final Throwable cause)
   {
      super(cause);
   }
}
