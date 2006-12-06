package uk.co.flamingpenguin.jewel.cli;

import java.lang.reflect.Method;

interface ArgumentSpecification
{
   String getName();
   Method getMethod();
   Class<?> getType();
   boolean isMultiValued();
}