package se.bechuchi.integration.exception;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class InvalidItemIdentifierTest {
    private InvalidItemIdentifierException exception;

    @Before
    public void setUp() {
        int itemIdentifier = 12345;
        LocalTime timestamp = LocalTime.of(10, 30, 15);
        exception = new InvalidItemIdentifierException(itemIdentifier, timestamp);
    }

    @After
    public void tearDown() {
        exception = null;
    }

    @Test
    public void testGetTimestampOfException() {
        LocalTime expectedTimestamp = LocalTime.of(10, 30, 15);
        assertEquals(expectedTimestamp, exception.getTimestampOfException());
    }

    @Test
    public void testGetItemIdentifier() {
        int expectedItemIdentifier = 12345;
        assertEquals(expectedItemIdentifier, exception.getItemIdentifier());
    }
}
