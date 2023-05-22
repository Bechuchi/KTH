package se.bechuchi.model.disount;

import se.bechuchi.integration.DiscountService;

/**
 * Discount based on the age of a customer resulting in a retiree discount.
 */
public class RetireeCustomer implements CustomerBehavior {
    /**
     * Checks if the customer is eligible for the retiree discount.
     */
    @Override
    public boolean isEligible(int customerID, DiscountService discountService) {
        return discountService.isCustomerEligibleForRetireeDiscount(customerID);
    }

    /**
     * Calculates the customers discount value.
     */
    @Override
    public double calculateCustomerDiscount(int customerID) {
        return 0.05;
    }

    private boolean isTodayWednesday() {
        return true;
    }
}
