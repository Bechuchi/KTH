package se.bechuchi.integration.exception;

import java.time.LocalTime;

/**
 * Represents an exception that can provide a timestamp.
 */
public interface TimestampedException {
    /**
     * Returns the timestamp associated with the exception.
     * 
     * @return The timestamp of the exception.
     */
    LocalTime getTimestampOfException();
}