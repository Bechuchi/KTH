package se.bechuchi.model.disount;

import java.util.List;

import se.bechuchi.model.dto.BoughtItemDTO;

/**
 * Items in the process sale can be of different types and depending on the type
 * a discount value can be adopted
 * to the total price of the process sale.
 */
public class ItemType implements ItemBehavior {
    /**
     * Should iterate over every item that is in the sale and retrieve information
     * about possible discount values
     * from the external discount database. Depending on the type of item different
     * discount will be adopted.
     */
    public double checkIfAnyItemHasSpecificItemTypeResultingWithDiscount(List<BoughtItemDTO> boughtItems) {
        for (BoughtItemDTO boughtItemDTO : boughtItems) {

        }

        return 0.05;
    }

    /**
     * Calculates the resulting discount based on the item type of the bought item.
     */
    @Override
    public double calculateDiscount(BoughtItemDTO item) {
        return 0.05;
    }
}
