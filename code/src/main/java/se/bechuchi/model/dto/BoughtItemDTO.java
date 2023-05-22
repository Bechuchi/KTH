package se.bechuchi.model.dto;

import se.bechuchi.model.Item;

/**
 * Represents an boughtItemDTO with all available information on this item
 * stored in
 * the external inventory system together with data that has been unique for
 * this process sale.
 * Data about an boughtItemDTO can only be read (get) and not written (set).
 */
public final class BoughtItemDTO {
    private int ID;
    private String name;
    private double price;
    private double VAT;
    private int quantity;

    /**
     * Creates a new instance of the bought item DTO to be send accross layers.
     * 
     * @param itm The item which have information to copy over to this instance.
     */
    public BoughtItemDTO(Item itm) {
        this.ID = itm.getID();
        this.name = itm.getName();
        this.price = itm.getPrice();
        this.VAT = itm.getVAT();
        this.quantity = itm.getQuantity();
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
     * Returns the item VAT rate.
     * 
     * @return VAT rate of this item.
     */
    public double getVAT() {
        return VAT;
    }

    /**
     * Returns the bought quantity of this item.
     * 
     * @return bought quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Prints the description of a bought item, that is the name price and quantity.
     * 
     * @return A concatination of how a bought item description should be printed.
     */
    public String getBoughtItmDescription() {
        return name + "\t\t$" + price + " * " + quantity;
    }
}
