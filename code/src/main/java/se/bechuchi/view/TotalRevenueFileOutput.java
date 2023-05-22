package se.bechuchi.view;

import se.bechuchi.model.SaleObserver;

/**
 * Responsible for logging information about total income to developers.
 */
public class TotalRevenueFileOutput implements SaleObserver {
    private FileLogger logger;

    /**
     * Creates a new instance of this output.
     * 
     * @param logger Where the information should be logged.
     */
    public TotalRevenueFileOutput(FileLogger logger) {
        this.logger = logger;
    }

    /**
     * When a new value has been made on the total income this method handles what
     * to do when that happends.
     */
    public void newTotalIncome(double totalIncome) {
        String text = "Total income: $" + totalIncome;
        logger.logTotalIncome(text);
    }
}
