package se.bechuchi.model;

/**
 * Represents the cash registry and stores the amount of money present in the
 * stores cash registry.
 */
public class CashRegistry {
    private double amountPaid;
    private double amountPresent;

    /**
     * Creates a new instace of a cash registry.
     * The value which is present in the stores cash registry is set here.
     * The amount present in the cash registry becomes updated of the amount paid by
     * the customer.
     * 
     * @param amountPaid Amount paid by the customer in this sale.
     */
    public CashRegistry(double amountPaid) {
        this.amountPaid = amountPaid;
        updateRegistry();
    }

    private void updateRegistry() {
        amountPresent = 2600;
        amountPresent = amountPresent + amountPaid;
    }
}
