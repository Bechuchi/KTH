package se.bechuchi.model;

/**
 * Represents the payment of a sale.
 */
public class Payment {
    private double totalPriceInclVAT;
    private double VATEntireSale;
    private double amountPaid;
    private double change;
    private CashRegistry cashRegistry;

    /**
     * Creates a new instace of this payment for this active sale.
     * 
     * @param totalPriceInclVAT Total price of the sale.
     * @param VATEntireSale     VAT value of the entire sale.
     * @param amountPaid        Amount paid by the customer.
     */
    public Payment(double totalPriceInclVAT, double VATEntireSale, double amountPaid) {
        this.totalPriceInclVAT = totalPriceInclVAT;
        this.cashRegistry = new CashRegistry(amountPaid);
        this.VATEntireSale = VATEntireSale;
        this.amountPaid = amountPaid;
        this.change = calculateChange();
    }

    /**
     * Returns the total price of the sale, including the VAT.
     * 
     * @return Total price including VAT.
     */
    public double getTotalPriceInclVAT() {
        return totalPriceInclVAT;
    }

    /**
     * Returns the VAT for the entire sale.
     * 
     * @return VAT for entire sale.
     */
    public double getVATEntireSale() {
        return VATEntireSale;
    }

    /**
     * Returns the amount paid by the customer.
     * 
     * @return Amount paid by customer.
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Returns the amount of change to give back to the customer.
     * 
     * @return Change to give customer.
     */
    public double getChange() {
        return change;
    }

    private double calculateChange() {
        return (amountPaid - totalPriceInclVAT);
    }
}
