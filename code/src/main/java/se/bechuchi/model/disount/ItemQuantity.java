package se.bechuchi.model.disount;

import java.util.List;

import se.bechuchi.model.dto.BoughtItemDTO;

/**
 * Depending on the quantity of a bought item the discount value can vary. This
 * is a representation of how to handle this.
 */
public class ItemQuantity implements ItemBehavior {

    /**
     * Iterates over the list of bought items and checks if the items have discount
     * connected to them.
     */
    public double checkIfAnyItemHasQuantityValueResultingWithDiscount(List<BoughtItemDTO> boughtItems) {
        for (BoughtItemDTO boughtItemDTO : boughtItems) {
            // Check for possible dicount value.
        }
        return 0.05;
    }

    /**
     * Calculates the resulting discount based on the quantity of the bought item.
     */
    @Override
    public double calculateDiscount(BoughtItemDTO item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateDiscount'");
    }
}