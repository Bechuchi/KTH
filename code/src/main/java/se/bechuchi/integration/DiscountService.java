package se.bechuchi.integration;

/**
 * Represents the service between this program and the external discount
 * database
 * system.
 */
public class DiscountService {
    /**
     * Checks if a customer is eligible for any type of discount.
     * 
     * @param customerID Identification value of the customer which is being checked
     *                   if discount exists.
     * @return Boolean value defining if there is any discount eligibility.
     */
    public boolean isCustomerElibibleForDiscount(int customerID) {
        if (isCustomerEligibleForRetireeDiscount(customerID) || isCustomerEligibleForPremiumDiscount(customerID)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the customer if eligible for retireee dicount based on age.
     * 
     * @param customerID Identification value of the customer which is being checked
     *                   if discount exists.
     * @return Boolean value defining if there is any retiree discount eligibility.
     */
    public boolean isCustomerEligibleForRetireeDiscount(int customerID) {
        if (customerID <= 1958)
            return true;
        else {
            return false;
        }
    }

    /**
     * Checks if the customer if eligible for premium dicount.
     * 
     * @param customerID Identification value of the customer which is being checked
     *                   if discount exists.
     * @return Boolean value defining if there is any premium discount eligibility.
     */
    public boolean isCustomerEligibleForPremiumDiscount(int customerID) {
        return false;
    }
}
