package se.bechuchi.integration;

import se.bechuchi.model.dto.SaleDTO;

/**
 * Represents the service between this program and the external accounting
 * system.
 */
public class AccountingService {
    private SaleDTO saleDTO;

    /**
     * Sends sale information to the external accounting system.
     * 
     * @param saleDTO The sale information which should be send externally.
     */
    public void sendSaleInfoToExternalAccountingSystem(SaleDTO saleDTO) {
        this.saleDTO = saleDTO;
    }

    /**
     * Returns the object saleDTO.
     * 
     * @return The object saleDTO which contains information that should be send
     *         externally.
     */
    public SaleDTO getSaleDTO() {
        return saleDTO;
    }
}
