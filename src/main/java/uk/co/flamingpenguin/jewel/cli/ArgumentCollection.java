package uk.co.flamingpenguin.jewel.cli;

import java.util.List;
import java.util.Map;

interface ArgumentCollection extends Iterable<Map.Entry<String, List<String>>>
{
   List<String> getUnparsed();
   
   /**
    * Are there any unparsed options available
    * 
    * @return are there any unparsed options available
    */
   boolean hasUnparsed();
   
   boolean contains(String ... options);
   boolean containsAny(List<String> options);
}
