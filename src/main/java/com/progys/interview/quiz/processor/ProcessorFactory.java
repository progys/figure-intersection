package com.progys.interview.quiz.processor;

import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.processor.InputProcessor;

import javax.inject.Named;
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
