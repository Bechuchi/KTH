package se.bechuchi.integration.exception;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.bechuchi.integration.exception.DatabaseFailureException;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class DatabaseFailureExceptionTest {
    private DatabaseFailureException exception;

    @Before
    public void setUp() {
        LocalTime timestamp = LocalTime.of(15, 45, 20);
        exception = new DatabaseFailureException(timestamp);
    }

    @After
    public void tearDown() {
        exception = null;
    }

    @Test
    public void testGetTimestampOfException() {
        LocalTime expectedTimestamp = LocalTime.of(15, 45, 20);
        assertEquals(expectedTimestamp, exception.getTimestampOfException());
    }
}
