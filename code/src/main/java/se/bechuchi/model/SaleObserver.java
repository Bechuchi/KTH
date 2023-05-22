package se.bechuchi.model;

/**
 * Interface to implement the Observer pattern in order to make all observers
 * get notified whenever a change has been made on the running total of the
 * sale.
 */
public interface SaleObserver {
    /**
     * Takes the new running total as in input parameter in order to send out the
     * correct data to all observers.
     * 
     * @param running
     */
    void newTotalIncome(double running);
}
