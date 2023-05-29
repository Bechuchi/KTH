package se.bechuchi.model;

/**
 * This abstract class defines the template method pattern. It calculates and
 * maintains the totalIncome field and provides a template for the algorithm
 * by calling the abstract methods doShowTotalIncome() and handleErrors(). It
 * also implements the SaleObserver interface and notifies the observers when
 * a new sale is made.
 */
public abstract class TemplateClass {
    protected double totalIncome;

    public final void newSaleWasMade(double priceOfTheSaleThatWasJustMade) {
        calculateTotalIncome(priceOfTheSaleThatWasJustMade);
        showTotalIncome();
    }

    private void calculateTotalIncome(double priceOfTheSale) {
        totalIncome += priceOfTheSale;
    }

    private void showTotalIncome() {
        try {
            doShowTotalIncome();
        } catch (Exception e) {
            handleErrors(e);
        }
    }

    protected abstract void doShowTotalIncome() throws Exception;

    protected abstract void handleErrors(Exception e);
}
