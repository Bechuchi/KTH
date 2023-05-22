package se.bechuchi.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import se.bechuchi.model.Payment;

/**
 * Represents the receipt which should be printed out at the end of the sale.
 * It contains all final information about this sale which is valuable for the
 * customer to read.
 */
public final class ReceiptDTO {
    private final List<BoughtItemDTO> bghtItms;
    private final double totalPriceInclVAT;
    private final double amtPaid;
    private final double change;
    private final double VATEntireSale;
    private final LocalTime time;
    private final LocalDate date;

    /**
     * Creates a new instance, representing the specified receipt with all of its
     * values set.
     * 
     * @param bghtItms List of all recorded items and their belonging information
     *                 from the scanning process of items the customer wants to
     *                 purchase.
     * @param paym     All information about the payment for this sale.
     */
    public ReceiptDTO(List<BoughtItemDTO> bghtItms, Payment paym) {
        this.bghtItms = bghtItms;
        this.totalPriceInclVAT = paym.getTotalPriceInclVAT();
        this.VATEntireSale = paym.getVATEntireSale();
        this.amtPaid = paym.getAmountPaid();
        this.change = paym.getChange();
        this.time = LocalTime.now();
        this.date = LocalDate.now();
    }

    /**
     * Returns the collection of all items which have been purchued by the customer
     * in the process sale.
     * 
     * @return All items which have been purchued by the customer in the process
     *         sale
     */
    public List<BoughtItemDTO> getBoughtItems() {
        return bghtItms;
    }

    /**
     * Returns the total price of the entire sale.
     * 
     * @return Total price of the entire sale.
     */
    public double getTotalPriceEntireSale() {
        return totalPriceInclVAT;
    }

    /**
     * Returns the VAT rate of the entire sale.
     * 
     * @return VAT rate of the entire sale.
     */
    public double getVATEntireSale() {
        return VATEntireSale;
    }

    /**
     * Returns the amount of change which should be given to the customer.
     * 
     * @return Amount of change which should be given to the customer.
     */
    public double getChange() {
        return change;
    }

    /**
     * Returns the amount paid by the customer.
     * 
     * @return Amount paid by the customer.
     */
    public double getAmountPaid() {
        return amtPaid;
    }

    /**
     * A representation of todays date.
     * 
     * @return date of today.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * A representation of the current time.
     * 
     * @return timestamp of the current moment.
     */
    public LocalTime getTime() {
        return time;
    }
}
