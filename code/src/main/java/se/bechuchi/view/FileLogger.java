package se.bechuchi.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Prints log messages to a file. The log file will be in the
 * current directory and will be called log.txt.
 */
public class FileLogger {
    private PrintWriter errorStream;
    private PrintWriter totalIncomeStream;

    /**
     * Creates a new instance and also creates a new log file.
     * An existing log file will be deleted.
     */
    public FileLogger() {
        try {
            errorStream = new PrintWriter(new FileWriter("errorReport.txt"), true);
            totalIncomeStream = new PrintWriter(new FileWriter("totalIncomeLog.txt"), true);
        } catch (IOException ioe) {
            System.out.println("CAN NOT LOG.");
            ioe.printStackTrace();
        }
    }

    /**
     * Prints the specified string to the log file.
     *
     * @param message The string that will be printed to the
     *                log file.
     */
    public void logError(String message) {
        errorStream.println(message);
    }

    /**
     * Loggs the total income to the file taking care of logging this information.
     * 
     * @param message Text to log to the file, containing the total income.
     */
    public void logTotalIncome(String message) {
        totalIncomeStream.println(message);
    }
}
