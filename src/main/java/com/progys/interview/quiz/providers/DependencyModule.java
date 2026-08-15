package com.progys.interview.quiz.providers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.progys.interview.quiz.Application;
import com.progys.interview.quiz.FigureIntersection;
import com.progys.interview.quiz.commands.*;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.parser.ActionNames;
import com.progys.interview.quiz.parser.CommandParser;
import com.progys.interview.quiz.parser.ConcreteParserFactory;
import com.progys.interview.quiz.parser.GeneralParserFactory;
import com.progys.interview.quiz.parser.ParsedAction;
import com.progys.interview.quiz.parser.Parser;
import com.progys.interview.quiz.parser.ParserFactory;
import com.progys.interview.quiz.parser.PointParser;
import com.progys.interview.quiz.parser.ShapeParser;
import com.progys.interview.quiz.persistence.ObjectStore;
import com.progys.interview.quiz.persistence.Store;
import com.progys.interview.quiz.processor.ConsoleInputProcessor;
import com.progys.interview.quiz.processor.FileInputProcessor;
import com.progys.interview.quiz.processor.InputProcessor;
import com.progys.interview.quiz.processor.ProcessorFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Guice DI configuration.
 * 
 * @author progys
 */
public class DependencyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Application.class).to(FigureIntersection.class);
        bind(Store.class).to(ObjectStore.class).asEagerSingleton();
        bind(CommandFactory.class).to(GeneralCommandFactory.class);
        bind(ParserFactory.class).to(GeneralParserFactory.class);

        install(new FactoryModuleBuilder()
                .implement(Command.class, Names.named(ActionNames.empty.name()),
                        EmptyCommand.class)
                .implement(Command.class, Names.named(ActionNames.clear.name()),
                        ClearCommand.class)
                .implement(Command.class, Names.named(ActionNames.exit.name()), ExitCommand.class)
                .implement(Command.class, Names.named(ActionNames.help.name()), HelpCommand.class)
                .implement(Command.class, Names.named(ActionNames.list.name()), ListCommand.class)
                .implement(Command.class, Names.named("point"), PointCommand.class)
                .implement(Command.class, Names.named("shape"),
                        ShapeCommand.class).build(ActionCommandFactory.class));

        install(new FactoryModuleBuilder()
                .implement(InputProcessor.class, Names.named("file"), FileInputProcessor.class)
                .implement(InputProcessor.class, Names.named("console"),
                        ConsoleInputProcessor.class).build(ProcessorFactory.class));

        install(new FactoryModuleBuilder()
                .implement(new TypeLiteral<Parser<Point>>() {}, Names.named("point"),
                        PointParser.class)
                .implement(new TypeLiteral<Parser<Shape>>() {}, Names.named("shape"),
                        ShapeParser.class)
                .implement(new TypeLiteral<Parser<ParsedAction>>() {}, Names.named("command"),
                        CommandParser.class)
                .build(ConcreteParserFactory.class));

    }

    @Provides
    public InputStream consoleProvider() {
        return System.in;
    }

    @Provides
    public PrintStream outputProvider() {
        return System.out;
    }

    @Provides
    @Singleton
    public EntityManagerFactory entityManagerFactoryProvider() {
        return Persistence.createEntityManagerFactory("./db/shapes.odb");
    }

}
