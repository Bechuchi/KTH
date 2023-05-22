package se.bechuchi.integration.exception;

import java.time.LocalTime;

/**
 * Thrown when the connection to the external database is lost.
 */
public class DatabaseFailureException extends RuntimeException implements TimestampedException {
    private LocalTime timestampOfException;

    /**
     * Creates an instance of this type of exception.
     * 
     * @param timeStampOfException
     */
    public DatabaseFailureException(LocalTime timeStampOfException) {
        this.timestampOfException = timeStampOfException;
    }

    /**
     * Defines the time of when the exception occured.
     */
    public LocalTime getTimestampOfException() {
        return timestampOfException;
    }
}
