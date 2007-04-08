package uk.co.flamingpenguin.jewel.cli.examples;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface HelpExample
{
   @Option
   int getCount();

   @Option(description = "your email address", pattern = "^[^\\S@]+@[\\w.]+$")
   String getEmail();

   @Option(description = "the location of something")
   String getLocation();

   @Option(description = "a pattern", shortName = "p")
   String getPattern();

   @Option(helpRequest=true, description = "display help", shortName = "h")
   boolean getHelp();
}
