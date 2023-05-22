package se.bechuchi.view;

import se.bechuchi.model.SaleObserver;

/**
 * Shows the total income of the process sale on the user interface.
 */
public class TotalRevenvueView implements SaleObserver {
    /**
     * When a new value has been made on the total income this method handles what
     * to do when that happends.
     */
    public void newTotalIncome(double totalIncome) {
        System.out.println("Total income: \t$" + totalIncome);
    }
}
