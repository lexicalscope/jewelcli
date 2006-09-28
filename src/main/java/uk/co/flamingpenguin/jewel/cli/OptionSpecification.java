package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

interface OptionSpecification
{
   Class<?> getType();

   String getName();

   String getShortName();

   String getLongName();

   String getDescription();

   boolean isMultiValued();

   boolean hasValue();

   boolean hasShortName();

   boolean isOptional();

   StringBuilder getSummary(StringBuilder result);

   Method getMethod();

   boolean patternMatches(String value);
}