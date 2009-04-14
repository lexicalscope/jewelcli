package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;
import java.util.List;

interface OptionsSpecification<O> extends Iterable<OptionSpecification>
{
   boolean isSpecified(final String key);

   boolean isSpecified(Method method);

   boolean isExistenceChecker(Method method);

   boolean isUnparsedExistenceChecker(Method method);

   OptionSpecification getSpecification(final String key);

   OptionSpecification getSpecification(final Method key);

   List<OptionSpecification> getMandatoryOptions();

   boolean hasUnparsedSpecification();

   OptionSpecification getUnparsedSpecification();

   boolean isUnparsedMethod(Method method);

}