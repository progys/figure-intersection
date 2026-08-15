package com.progys.interview.quiz.persistence;

/**
 * Thrown when a persistence operation fails.
 * 
 * @author progys
 */
public class StoreException extends RuntimeException {
    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
