package com.lexicalscope.jewelcli.maven.report;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static ch.lambdaj.Lambda.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

import ch.lambdaj.function.convert.Converter;

import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.HelpMessage;
import com.lexicalscope.jewel.cli.OptionHelpMessage;

/**
 * Goal which produces a CLI help page for you maven site
 * 
 * @author Tim Wood
 * @goal report
 * @execute phase="compile" lifecycle="jewelcli"
 * @requiresDependencyResolution compile
 * @requiresProject true
 * @requiresReports true
 */
public class JewelCliReportMojo
extends AbstractMavenReport
{
    private static final class AddDash implements Converter<String, String> {
        @Override public String convert(final String string) {
            return "-" + string;
        }
    }

    private static final class AddDashDash implements Converter<String, String> {
        @Override public String convert(final String string) {
            return "--" + string;
        }
    }

    /**
     * Directory where reports will go.
     * 
     * @parameter expression="${project.reporting.outputDirectory}"
     * @required
     * @readonly
     */
    private String outputDirectory;

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @component
     * @required
     * @readonly
     */
    private Renderer siteRenderer;

    /**
     * The interfaces to output
     * 
     * @parameter
     * @required
     */
    private String[] interfaces;

    @Override public String getDescription(final Locale locale) {
        return getBundle(locale).getString("report.jewelcli.description");
    }

    @Override public String getName(final Locale locale) {
        return getBundle(locale).getString("report.jewelcli.name");
    }

    String[] getInterfaces() {
        return interfaces;
    }

    void setInterfaces(final String[] interfaces) {
        this.interfaces = interfaces;
    }

    @Override public String getOutputName() {
        return "command-line-interface";
    }

    @Override protected void executeReport(final Locale locale) throws MavenReportException {
        final ClassLoader classLoader = getClassLoader();
        final Class<?> interfaceClass = interfaceClass(classLoader);

        final Cli<?> cli = CliFactory.createCli(interfaceClass);
        getLog().debug("cli interface with  " + cli);

        final Sink sink = getSink();
        sink.head();
        sink.title();
        sink.text(getName(locale));
        sink.title_();
        sink.head_();

        sink.body();
        sink.section1();

        sink.sectionTitle1();
        sink.text(getBundle(locale).getString("report.jewelcli.title") + " " + getProject().getName());
        sink.sectionTitle1_();
        sink.lineBreak();

        sink.text("Command line interface for " + " " + getProject().getName());
        sink.lineBreak();
        sink.lineBreak();

        cli.describeTo(new HelpMessage() {
            @Override public void startOfOptions() {
                sink.table();
                sink.tableRow();

                sink.tableHeaderCell();
                sink.text("Mandatory");
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text("Long Form");
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text("Short Form");
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text("Pattern");
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text("Description");
                sink.tableHeaderCell_();

                sink.tableRow_();
            }

            @Override public OptionHelpMessage option() {
                return new OptionHelpMessage() {
                    @Override public void startOptionalOption() {
                        sink.tableRow();
                        sink.tableCell("true");
                    }

                    @Override public void startMandatoryOption() {
                        sink.tableRow();
                        sink.tableCell();
                        sink.text("true");
                        sink.tableCell_();
                    }

                    @Override public void singleValuedWithCustomPattern(final String pattern) {
                        sink.tableCell();
                        sink.text("/" + pattern + "/");
                        sink.tableCell_();
                    }

                    @Override public void singleValued() {
                        sink.tableCell();
                        sink.text("/.*/");
                        sink.tableCell_();
                    }

                    @Override public void noValued() {
                        sink.tableCell();
                        sink.tableCell_();
                    }

                    @Override public void shortName(final List<String> shortNames) {
                        sink.tableCell();
                        sink.text(join(convert(shortNames, new AddDash()), ", "));
                        sink.tableCell_();
                    }

                    @Override public void multiValuedWithCustomPattern() {
                        sink.tableCell();
                        sink.text("/.*/...");
                        sink.tableCell_();
                    }

                    @Override public void multiValuedWithCustomPattern(final String pattern) {
                        sink.tableCell();
                        sink.text("/" + pattern + "/...");
                        sink.tableCell_();
                    }

                    @Override public void longName(final List<String> longNames) {
                        sink.tableCell();
                        sink.text(join(convert(longNames, new AddDashDash()), ", "));
                        sink.tableCell_();
                    }

                    @Override public void endOptionalOption(final String description) {
                        sink.tableCell();
                        sink.text(description);
                        sink.tableCell_();
                        sink.tableRow_();
                    }

                    @Override public void endOptionalOption() {
                        sink.tableCell();
                        sink.tableCell_();
                        sink.tableRow_();
                    }

                    @Override public void endMandatoryOption(final String description) {
                        sink.tableCell();
                        sink.text(description);
                        sink.tableCell_();
                        sink.tableRow_();
                    }

                    @Override public void endMandatoryOption() {
                        sink.tableCell();
                        sink.tableCell_();
                        sink.tableRow_();
                    }
                };
            }

            @Override public void noUsageInformation() {
                // nothing
            }

            @Override public void hasUsageInformation() {
                sink.text("Usage: ");
            }

            @Override public void hasUsageInformation(final String applicationName) {
                sink.text("Usage: " + applicationName + " ");
            }

            @Override public void hasUnparsedOption(final String valueName) {
                sink.text(valueName);
            }

            @Override public void hasUnparsedMultiValuedOption(final String valueName) {
                sink.text(valueName + "...");
            }

            @Override public void hasSomeMandatoryOptions() {
                sink.text("options ");
            }

            @Override public void hasOnlyOptionalOptions() {
                sink.text("[options] ");
            }

            @Override public void endOfOptions() {
                sink.table_();
            };
        });

        sink.lineBreak();
        sink.section1_();
        sink.body_();
        sink.flush();
        sink.close();
    }

    private Class<?> interfaceClass(final ClassLoader classLoader) throws MavenReportException {
        final String interfac3 = interfaces[0];
        getLog().debug("generating command line interface report for " + interfac3);
        try {
            return classLoader.loadClass(interfac3);
        } catch (final ClassNotFoundException e) {
            throw new MavenReportException("Unable to load interface class " + interfac3, e);
        }
    }
    @Override protected String getOutputDirectory() {
        return outputDirectory;
    }

    @Override protected MavenProject getProject() {
        return project;
    }

    @Override protected Renderer getSiteRenderer() {
        return siteRenderer;
    }

    private ResourceBundle getBundle(final Locale locale)
    {
        return ResourceBundle.getBundle("jewelcli-report", locale, this.getClass().getClassLoader());
    }

    private ClassLoader getClassLoader() throws MavenReportException {
        final List<URL> urls = new ArrayList<URL>();
        try {
            for (final Object object : project.getCompileClasspathElements()) {
                final String path = (String) object;
                final URL url = new File(path).toURL();

                getLog().debug("adding classpath element " + url);

                urls.add(url);
            }
        } catch (final MalformedURLException e) {
            throw new MavenReportException("Unable to load command line interface class", e);
        } catch (final DependencyResolutionRequiredException e) {
            throw new MavenReportException("Unable to resolve dependencies of project", e);
        }

        return new URLClassLoader(urls.toArray(new URL[] {}), getClass().getClassLoader());
    }
}
