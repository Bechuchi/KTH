package se.bechuchi.integration.exception;

import java.time.LocalTime;

/**
 * Thrown when an input item identifier is not found in the external inventory
 * system.
 */
public class InvalidItemIdentifierException extends Exception implements TimestampedException {
    private int itemIdentifier;
    private LocalTime timestampOfException;

    /**
     * Creates an instance of this type of exception. Defined with the item
     * identifier for when this exception occured
     * and also the time of when the exception happended.
     */
    public InvalidItemIdentifierException(int itmIdentifier, LocalTime timestampOfException) {
        this.itemIdentifier = itmIdentifier;
        this.timestampOfException = timestampOfException;
    }

    /**
     * Defines the time of when the exception occured.
     */
    public LocalTime getTimestampOfException() {
        return timestampOfException;
    }

    /**
     * Returns the item identifier that made this exception occur.
     * 
     * @return The invalid item identifier.
     */
    public int getItemIdentifier() {
        return itemIdentifier;
    }
}
