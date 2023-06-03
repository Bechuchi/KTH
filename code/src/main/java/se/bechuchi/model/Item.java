package se.bechuchi.model;

import se.bechuchi.model.dto.ItemDTO;

/**
 * Represents the information of a sold item. The quantity value of an item can
 * be updated during execution. The other values have been collected from the
 * stores inventory system at the first time an item with the specified
 * identifier was
 * registered in this sale.
 */
public class Item {
    private int ID;
    private String name;
    private double price;
    private double VAT;
    private int quantity;

    /**
     * Creates a new instance of an item.
     * 
     * @param itemDTO Item information collected from the external inventory system.
     *                This information is copied over to this representation of an
     *                item.
     */
    public Item(ItemDTO itemDTO) {
        this.ID = itemDTO.getID();
        this.name = itemDTO.getName();
        this.price = itemDTO.getPrice();
        this.VAT = itemDTO.getVAT();
        this.quantity = 1;
    }

    /**
     * Returns the item identifier.
     * 
     * @return Item identifier of this item.
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the item name.
     * 
     * @return Name of this item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the item price.
     * 
     * @return Price of this item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the quantity that has been registered during this sale.
     * 
     * @return Sold quantity of this item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the item VAT rate.
     * 
     * @return VAT rate of this item.
     */
    public double getVAT() {
        return VAT;
    }

    /**
     * Prints the description of an item, that is the name price and quantity.
     * 
     * @return A concatination of how an item description should be printed.
     */
    public String getFormattedItemDescription() {
        return name + "\t\t$" + price + " * " + quantity;
    }

    /**
     * Increases the quantity that has been sold with this
     * items same identifier.
     * 
     * @param quantityToAdd The additional quantity to add into the currently stored
     *                      value of quantity.
     */
    void increaseSoldQuantity(int quantityToAdd) {
        quantity = quantity + quantityToAdd;
    }
}
