/**
 *
 */
package com.lexicalscope.jewel;

/**
 * The superclass of all Jewel Runtime Exception
 *
 * @author tim
 */
public class JewelRuntimeException extends RuntimeException
{
   private static final long serialVersionUID = 6028801435353855311L;

   /**
    * A new exception with no message
    */
   public JewelRuntimeException()
   {
      super();
   }

   /**
    * A new exception with the given message and cause
    *
    * @param message The message
    * @param cause The cause
    */
   public JewelRuntimeException(final String message, final Throwable cause)
   {
      super(message, cause);
   }

   /**
    * A new exception with the given message
    *
    * @param message The message
    */
   public JewelRuntimeException(final String message)
   {
      super(message);
   }

   /**
    * A new exception with the given cause
    *
    * @param cause The cause
    */
   public JewelRuntimeException(final Throwable cause)
   {
      super(cause);
   }
}
