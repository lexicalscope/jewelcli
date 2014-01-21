package com.lexicalscope.jewel.issues;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

public class Github26_1 {
	interface Options {
	    @Option(shortName = "f")
	    boolean f();
	}

    @Test public void main() {
        final Options options = CliFactory.parseArguments(Options.class, "-f");
        assertThat("f is set", options.f());
      }
}
