package com.lexicalscope.jewel.issues;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

public class Github20 {
   public static class MyList extends ArrayList<String> {
      public MyList(final String string) {
        this.addAll(Arrays.asList(string.split(";;")));
      }
    }

    public interface Options {
      @Option
      MyList getList();
    }

    @Test public void main() {
      final Options options = CliFactory.parseArguments(Options.class, "--list", "foo;;bar");
      assertThat(options.getList(), contains("foo", "bar"));
    }
}
