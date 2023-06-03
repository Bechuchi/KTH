package se.bechuchi.model.dto;

import se.bechuchi.model.Item;

/**
 * Represents the information which should be presented to the customer and
 * cashier after an individual item has been scanned.
 * Every time a new item has been scanned there should be a new instance of
 * this class created.
 */
public final class RunningDisplayInformationDTO {
    private final String itmDescription;
    private final double runningTotal;

    /**
     * Creates a new instance of this information to display to the customer and
     * cashier.
     * 
     * @param itm          The current item which should be displayed.
     * @param runningTotal The running total of the sale which should be displayed.
     */
    public RunningDisplayInformationDTO(Item itm, double runningTotal) {
        this.itmDescription = itm.getFormattedItemDescription();
        this.runningTotal = runningTotal;
    }

    /**
     * Returns the item description of the item which has been scanned.
     * 
     * @return The item descrpition.
     */
    public String getItemDescription() {
        return itmDescription;
    }

    /**
     * Returns the running total of the whole sale.
     * 
     * @return The current value of the running total.
     */
    public double getRunningTotal() {
        return runningTotal;
    }

    /*
     * public boolean equals(Object comparingObject) {
     * return (item.getID() == ((DisplayInformationDTO)
     * comparingObject).item.getID());
     * }
     */
}
