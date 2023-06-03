package se.bechuchi.model.disount;

import se.bechuchi.integration.DiscountService;

/**
 * Encapsulates the common algorithms which can occur for a customer in respect
 * to dicount requests.
 */
public interface CustomerBehavior {
    /**
     * Checks if a customer is eligible for discount.
     * 
     * @param customerID      ID of the customer.
     * @param discountService Service used for discount calls to the external
     *                        discount database.
     * @return Boolean value showing if the customer was eligible or not.
     */
    boolean isEligible(int customerID, DiscountService discountService);

    /**
     * Calculates the discount value for a customer.
     * 
     * @param customerID ID of the customer.
     * @return Value of discount.
     */
    double calculateCustomerDiscount(int customerID);
}