package se.bechuchi.model.dto;

/**
 * Represents an itemDTO with all available information on this item stored in
 * the external inventory system.
 * Data about an itemDTO can only be read (get) and not written (set).
 */
public final class ItemDTO {
    private final int ID;
    private final String name;
    private final double price;
    private final double VAT;

    /**
     * Creates a new instance and sets all attributes which describes an itemDTO.
     * 
     * @param ID    Identifier of the item.
     * @param name  Name of the item.
     * @param price Price of the item.
     * @param VAT   VAT rate of the item.
     */
    public ItemDTO(int ID, String name, double price, double VAT) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.VAT = VAT;
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
}
