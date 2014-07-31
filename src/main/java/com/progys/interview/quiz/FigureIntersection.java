package com.progys.interview.quiz;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.progys.interview.quiz.persistence.Store;
import com.progys.interview.quiz.processor.ProcessorFactory;
import com.progys.interview.quiz.providers.DependencyModule;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

/**
 * Main class for running FigureIntersection.
 * 
 * @author progys
 */
@Singleton
public class FigureIntersection implements Application {
    @Option(name = "-f", usage = "input from this file", metaVar = "INPUT")
    private File inputFile = null;

    private Store persistence;
    private final ProcessorFactory processorFactory;

    @Inject
    FigureIntersection(Store persistence, ProcessorFactory processorFactory) {
        this.persistence = persistence;
        this.processorFactory = processorFactory;
    }

    private void handleFile() {
        if (inputFile == null) {
            return;
        }

        processorFactory.getProcessor(inputFile).process();
    }

    private void handleConsole() {
        processorFactory.getProcessor().process();
    }

    public void execute(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            handleFile();
            handleConsole();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        } finally {
            persistence.close();
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DependencyModule());

        Application application = injector.getInstance(Application.class);
        application.execute(args);
    }
}
