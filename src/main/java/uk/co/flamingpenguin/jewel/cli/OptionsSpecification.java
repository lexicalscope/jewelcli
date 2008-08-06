package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.List;

interface OptionsSpecification<O> extends Iterable<OptionMethodSpecification>
{
   boolean isSpecified(final String key);

   boolean isSpecified(Method method);

   boolean isExistenceChecker(Method method);

   OptionMethodSpecification getSpecification(final String key);

   OptionMethodSpecification getSpecification(final Method key);

   List<OptionMethodSpecification> getMandatoryOptions();

   boolean hasUnparsedSpecification();

   ArgumentMethodSpecification getUnparsedSpecification();
}