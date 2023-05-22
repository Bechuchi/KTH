package se.bechuchi.model.disount;

import java.util.List;

import se.bechuchi.integration.DiscountService;
import se.bechuchi.model.dto.BoughtItemDTO;

/**
 * Abstract class as the head of the strategy pattern in order to implement
 * validation and calculation of possible discount requests.
 */
public abstract class Discount {
    private double totalPriceBeforeDiscount;
    private ItemBehavior itemBehavior;
    private CustomerBehavior customerBehavior;
    private int customerID;
    private DiscountService discServ;

    /**
     * Creates an instance of this discount representation.
     * 
     * @param totalPriceBeforeDiscount The value of price for the process sale
     *                                 without any discount added to it.
     * @param itemBehavior             Common algorithms for how bought items can be
     *                                 handled in a discount request.
     * @param customerBehavior         Common algorithms for how the customer for
     *                                 whom the discount request is being made for
     *                                 can be handeled.
     * @param customerID               Identification value of the customer.
     * @param discServ                 Discount service responsible for connecting
     *                                 the external dicount database.
     */
    public Discount(double totalPriceBeforeDiscount, ItemBehavior itemBehavior, CustomerBehavior customerBehavior,
            int customerID,
            DiscountService discServ) {
        this.totalPriceBeforeDiscount = totalPriceBeforeDiscount;
        this.itemBehavior = itemBehavior;
        this.customerBehavior = customerBehavior;
        this.customerID = customerID;
        this.discServ = discServ;
    }

    /**
     * Checks if the customer is eligible for the discount based on the customer's
     * ID.
     *
     * @param customerID Identification value of the customer.
     * @return true if the customer is eligible for the discount, false otherwise.
     */
    public boolean isCustomerEligible() {
        return customerBehavior.isEligible(customerID, discServ);
    }

    /**
     * Calculates the discount applicable to the customer based on the customer's
     * ID.
     *
     * @param customerID Identification value of the customer.
     * @return the discount amount or percentage for the customer.
     */
    public double calculateCustomerDiscount(int customerID) {
        return customerBehavior.calculateCustomerDiscount(customerID);
    }

    /**
     * Calculates the discount for each item in the purchased items collection and
     * returns the total discount amount.
     *
     * @param boughtItems The collection of items that have been purchased.
     * @return the total discount amount for the purchased items.
     */
    public double calculateItemDiscount(List<BoughtItemDTO> boughtItems) {
        double totalDiscount = 0.0;
        for (BoughtItemDTO item : boughtItems) {
            double itemDiscount = itemBehavior.calculateDiscount(item);
            totalDiscount += itemDiscount;
        }
        return totalDiscount;
    }

    /**
     * Calculates the final discount value for the total price. This method should
     * be responsible for combining the results from the customer behavior and item
     * behavior.
     *
     * @param customerID  Identification value of the customer.
     * @param boughtItems The collection of items that have been purchased.
     * @return the final discounted total price.
     */
    public double calculateFinalDiscountValue(double totalPriceBeforeDiscunt, int customerID,
            List<BoughtItemDTO> boughtItems) {
        double itemDiscount = calculateItemDiscount(boughtItems);
        double customerDiscount = calculateCustomerDiscount(customerID);

        double totalPriceAfterDiscount = totalPriceBeforeDiscount - itemDiscount - customerDiscount;
        return totalPriceAfterDiscount;
    }
}
