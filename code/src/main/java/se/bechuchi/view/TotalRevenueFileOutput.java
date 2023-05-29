package se.bechuchi.view;

import se.bechuchi.model.TemplateClass;

/**
 * Responsible for logging information about total income to developers.
 */
public class TotalRevenueFileOutput extends TemplateClass {
    private FileLogger logger;

    /**
     * Creates a new instance of this output.
     * 
     * @param logger Where the information should be logged.
     */
    public TotalRevenueFileOutput(FileLogger logger) {
        this.logger = logger;
    }

    @Override
    protected void doShowTotalIncome() throws Exception {
        String text = "Total income: $" + totalIncome;
        logger.logTotalIncome(text);
    }

    @Override
    protected void handleErrors(Exception e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleErrors'");
    }
}
