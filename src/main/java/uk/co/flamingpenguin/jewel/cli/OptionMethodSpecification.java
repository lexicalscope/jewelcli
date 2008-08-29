package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

interface OptionMethodSpecification extends OptionSpecification, ArgumentMethodSpecification
{
   StringBuilder getSummary(StringBuilder result);

   List<String> getAllNames();
}