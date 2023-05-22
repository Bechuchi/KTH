package se.bechuchi.model.disount;

import se.bechuchi.integration.DiscountService;

/**
 * Defines a premium dicsount which only some customers are eligible for and
 * gets certain benefits.
 */
public class PremiumCustomer implements CustomerBehavior {
    /**
     * Checks if the customer is eligible for the premium discount.
     */
    @Override
    public boolean isEligible(int customerID, DiscountService discountService) {
        return discountService.isCustomerEligibleForPremiumDiscount(customerID);
    }

    /**
     * Calculates the customers discount value.
     */
    @Override
    public double calculateCustomerDiscount(int customerID) {
        return 0.05;
    }
}
