package com.progys.interview.quiz.processor;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

import java.io.File;

/**
 * @author progys
 */
public interface ProcessorFactory {
    @Named("file")
    InputProcessor getProcessor(@Assisted File file);

    @Named("console")
    InputProcessor getProcessor();
}
