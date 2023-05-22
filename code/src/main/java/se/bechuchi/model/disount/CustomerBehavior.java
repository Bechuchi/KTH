package se.bechuchi.model.disount;

import se.bechuchi.integration.DiscountService;

/**
 * Encapsulates the common algorithms which can occur for a customer in respect
 * to dicount requests.
 */
public interface CustomerBehavior {
    boolean isEligible(int customerID, DiscountService discountService);

    double calculateCustomerDiscount(int customerID);
}