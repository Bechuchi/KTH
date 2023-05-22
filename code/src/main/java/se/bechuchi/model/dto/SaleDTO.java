package se.bechuchi.model.dto;

import se.bechuchi.model.Sale;

/**
 * When a sale has been completed and the information about that sale should be
 * send externally then it is this instance which is created and sent away.
 */
public final class SaleDTO {
    private final Sale sale;

    /**
     * Creates a new instance of this class.
     * 
     * @param sale Sale information which should be sent externally.
     */
    public SaleDTO(Sale sale) {
        this.sale = sale;
    }
}
