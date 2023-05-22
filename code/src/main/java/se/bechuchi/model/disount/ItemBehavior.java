package se.bechuchi.model.disount;

import java.util.List;

import se.bechuchi.model.dto.BoughtItemDTO;

/**
 * Encapsulates the common algorithms which can occur for a bought item in
 * respect
 * to dicount requests for a customer.
 */
public interface ItemBehavior {
    /**
     * Calculates the discount of a bought item.
     * 
     * @param item Bought item to examin.
     * @return Returns the discount value.
     */
    public double calculateDiscount(BoughtItemDTO item);
}