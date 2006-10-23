package uk.co.flamingpenguin.jewel.cli;

class CliSpecificationImpl
{
   private final String m_message;

   public CliSpecificationImpl(final CommandLineInterface cliSpecification, final UnparsedSpecificationImpl unparsedSpecification, final boolean manditoryOptions)
   {
      if(cliSpecification == null && unparsedSpecification == null)
      {
         m_message = "The options available are:";
      }
      else
      {
         final StringBuilder message = new StringBuilder("Usage: ");

         if(cliSpecification != null && !nullOrBlank(cliSpecification.application()))
         {
            message.append(String.format("%s ", cliSpecification.application().trim()));
         }

         if(!manditoryOptions)
         {
            message.append("[");
         }
         message.append("options");
         if(!manditoryOptions)
         {
            message.append("]");
         }
         message.append(" ");

         if(unparsedSpecification != null)
         {
            final String unparsedName = !nullOrBlank(unparsedSpecification.getLongName())
                                           ? unparsedSpecification.getLongName()
                                           : "ARGUMENTS";
            message.append(unparsedName);

            if(unparsedSpecification.isMultiValued())
            {
               message.append("...");
            }
         }
         m_message = message.toString().trim();
      }
   }

   private boolean nullOrBlank(final String string)
   {
      return (string == null || string.trim().equals(""));
   }

   @Override
   public String toString()
   {
      return m_message;
   }
}
