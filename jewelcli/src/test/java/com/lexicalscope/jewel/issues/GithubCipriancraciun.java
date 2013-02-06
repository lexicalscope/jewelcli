package com.lexicalscope.jewel.issues;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

public class GithubCipriancraciun {
   public interface Options {
      @Option
      List<String> getKey();
    }

   @Test public void repeatedMultiValuedActionsAreAddedToList()
   {
      final Options options = CliFactory.parseArguments(Options.class, "--key",  "value1", "--key", "value2", "--key", "value3");
      assertThat(options.getKey(), contains("value1", "value2", "value3"));
   }
}
