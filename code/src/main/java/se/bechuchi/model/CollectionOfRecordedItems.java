package se.bechuchi.model;

import java.util.ArrayList;
import java.util.List;
import se.bechuchi.model.dto.ItemDTO;

/**
 * The collection of all items which have been registered to the sale are
 * processed here.
 */
public class CollectionOfRecordedItems {
    private List<Item> recordedItems;

    /**
     * Creates a new instance of the collection of recorded items.
     */
    public CollectionOfRecordedItems() {
        this.recordedItems = new ArrayList<Item>();
    }

    /**
     * Checks if an item with the same input identifier already has been recorded
     * into the sale.
     * 
     * @param inputID Input item identifier of the currently scanned item.
     * @return Boolean value showing if an item with a matching identifier exists to
     *         what the input item identifier is.
     */
    boolean isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(int inputID) {
        for (int i = 0; i < recordedItems.size(); i++) {
            if (recordedItems.get(i).getID() == inputID) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new item to the collectiong of recorded items in this sale.
     * 
     * @param itmDTO The information collected from the external inventory system
     *               with all fixed information
     *               about this current item.
     * @return The new representation of an item, which has the additional attribute
     *         quantity to it. Initially
     *         set to one.
     */
    public Item addNewItmToListOfRecordedItems(ItemDTO itmDTO) {
        Item itm = new Item(itmDTO);
        recordedItems.add(itm);

        return itm;
    }

    /**
     * Increases the quantity by 1 of an already recorded item.
     * 
     * @param inputID Item identifier of the item which should have its quantity
     *                increased.
     * @return The updated item which has had its quantity increased.
     */
    Item increaseQuantityOfAlreadyRecordItem(int inputID) {
        return getItem(inputID);
    }

    /**
     * Returns the list of items that have been recorded so far in this sale.
     * 
     * @return The list of recorded items.
     */
    public List<Item> getCollectionOfRecordedItems() {
        return recordedItems;
    }

    private Item getItem(int itmID) {
        for (int i = 0; i < recordedItems.size(); i++) {
            if (recordedItems.get(i).getID() == itmID) {
                recordedItems.get(i).increaseSoldQuantity(1);
                return recordedItems.get(i);
            }
        }
        return null;
    }
}
