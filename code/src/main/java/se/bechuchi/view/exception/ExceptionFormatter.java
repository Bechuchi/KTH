package se.bechuchi.view.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.integration.exception.TimestampedException;

/**
 * Formats exceptions so they are well presented to the view.
 */
public class ExceptionFormatter {
    /**
     * Prints to systemout the exception which should be shown to the user.
     * 
     * @param e Exception which has occured.
     */
    public void printUserExceptionMessage(Exception e) {
        System.out.println(getErrorMessage(e));
    }

    /**
     * Returns the exception message that should be given to developers.
     * 
     * @param e Exception which has occured.
     * @return The developer exception message in a well format.
     */
    public String getDeveloperExceptionMessage(Exception e) {
        String exceptionName = getExceptionName(e);
        String formattedTimestamp = getFormattedTimestamp(e);
        String errorMessage = getErrorMessage(e);

        return exceptionName + "\n" + formattedTimestamp + errorMessage + "\n";
    }

    private String getFormattedTimestamp(Exception e) {
        TimestampedException ex = (TimestampedException) e;
        LocalTime timestamp = ex.getTimestampOfException();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return "Timestamp:\t" + timestamp.format(formatter) + "\n";
    }

    private String getErrorMessage(Exception e) {
        if (e instanceof InvalidItemIdentifierException) {
            InvalidItemIdentifierException ex = (InvalidItemIdentifierException) e;

            return String.format(ExceptionMessages.INVALID_ITEM_IDENTIFIER, ex.getItemIdentifier());
        } else if (e instanceof DatabaseFailureException) {

            return ExceptionMessages.DATABASE_FAILURE;
        }

        return e.getMessage();
    }

    private String getExceptionName(Exception exception) {
        return exception.getClass().getSimpleName();
    }
}