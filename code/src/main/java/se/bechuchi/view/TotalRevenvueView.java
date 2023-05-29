package se.bechuchi.view;

import se.bechuchi.model.TemplateClass;

/**
 * Shows the total income of the process sale on the user interface.
 */
public class TotalRevenvueView extends TemplateClass {
    @Override
    protected void doShowTotalIncome() throws Exception {
        System.out.println("Total income: \t$" + totalIncome);
    }

    @Override
    protected void handleErrors(Exception e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleErrors'");
    }
}
