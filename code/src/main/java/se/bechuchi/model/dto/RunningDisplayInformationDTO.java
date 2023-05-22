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
        this.itmDescription = itm.getItmDescription();
        this.runningTotal = runningTotal;
    }

    /**
     * Returns the text as a summary of the information which should be
     * displayed to the cashier and the customer.
     */
    public String getRunningDisplayInformation() {
        return itmDescription + "\n" + "Running Total: \t$" + runningTotal;
    }

    /*
     * public boolean equals(Object comparingObject) {
     * return (item.getID() == ((DisplayInformationDTO)
     * comparingObject).item.getID());
     * }
     */
}
