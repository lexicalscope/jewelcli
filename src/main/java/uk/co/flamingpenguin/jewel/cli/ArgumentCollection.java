package uk.co.flamingpenguin.jewel.cli;

import java.util.List;
import java.util.Map;

interface ArgumentCollection extends Iterable<Map.Entry<String, List<String>>>
{
   /**
    * Get any unparsed arguments
    * 
    * @return the unparsed arguments 
    */
   List<String> getUnparsed();
   
   /**
    * Are there any unparsed options available
    * 
    * @return are there any unparsed options available
    */
   boolean hasUnparsed();
   
   /**
    * Does the collection contain any of the given options
    * 
    * @param options the options
    * 
    * @return true iff the collection contain any of the given options
    */
   boolean containsAny(String ... options);

   /**
    * Does the collection contain any of the given options
    * 
    * @param options the options
    * 
    * @return true iff the collection contain any of the given options
    */
   boolean containsAny(List<String> options);
   
   /**
    * Get the values associated with the given options
    * 
    * @param options the options to obtain values for
    * 
    * @return the values associated with the given options
    */
   List<String> getValues(String ... options);
   
   /**
    * Get the values associated with the given options
    * 
    * @param options the options to obtain values for
    * 
    * @return the values associated with the given options
    */
   List<String> getValues(List<String> options);
}
