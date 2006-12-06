package uk.co.flamingpenguin.jewel.cli;

import java.util.List;
import java.util.Map;

interface ArgumentCollection extends Iterable<Map.Entry<String, List<String>>>
{
   List<String> getUnparsed();
   boolean contains(String ... options);
   boolean containsAny(final List<String> options);
}
