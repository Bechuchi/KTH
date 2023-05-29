package se.bechuchi.model;

import java.util.ArrayList;
import java.util.List;

import se.bechuchi.integration.AccountingService;
import se.bechuchi.integration.InventoryService;
import se.bechuchi.model.dto.BoughtItemDTO;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.model.dto.ReceiptDTO;
import se.bechuchi.model.dto.SaleDTO;
import se.bechuchi.model.dto.RunningDisplayInformationDTO;

/**
 * Represents the sale and is the once class in the model layer which is being
 * called from the controller.
 */
public class Sale {
    private InventoryService invServ;
    private AccountingService accServ;
    private Payment payment;
    private List<TemplateClass> saleObservers = new ArrayList<>();
    private List<BoughtItemDTO> boghtItms = new ArrayList<>();
    private CollectionOfRecordedItems recrdItms;
    private double runningTotalInclVAT;
    private double totalPriceInclVAT;
    private double VATEntireSale;

    /**
     * Creates a new instance of this sale.
     * 
     * @param invServ Enables communication to the external inventory system.
     * @param accServ Enables communication to the external accounting system.
     */
    public Sale(InventoryService invServ, AccountingService accServ) {
        this.recrdItms = new CollectionOfRecordedItems();
        this.invServ = invServ;
        this.accServ = accServ;
    }

    /**
     * Checks if an item with the specified identifier already has been recorded to
     * the sale.
     * 
     * @param inputID Item identifier from the item that has been scanned.
     * @return Boolean value showing if the identifier is recognized from before or
     *         not.
     */
    public boolean hasIdentifierBeenRecordBefore(int inputID) {
        if (recrdItms.isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(inputID) == true) {
            return true;
        }
        return false;
    }

    /**
     * Adds a new item to the sale. An item with the same identifier has never been
     * recorded before
     * to this sale.
     * 
     * @param itmDTO Information about this item which has been collected from
     *               external inventory system.
     * @return Presented information for the customer and cashier which has been
     *         updated after the currently scanned item has been recorded to the
     *         sale.
     */
    public RunningDisplayInformationDTO addNewItmToSale(ItemDTO itmDTO) {
        Item newItm = recrdItms.addNewItmToListOfRecordedItems(itmDTO);
        updateRunningTotalInclVAT(newItm.getPrice(), newItm.getVAT());
        updateVATEntireSale(newItm.getVAT());

        return new RunningDisplayInformationDTO(newItm, runningTotalInclVAT);
    }

    /**
     * When an item with the specified input identifier already has been recorded to
     * the sale, this item needs to have its quantity updated and this is performed
     * here.
     * 
     * @param inputID Item identifier of the item to update.
     * @return Presented information for the customer and cashier which has been
     *         updated after the currently scanned item has been recorded to the
     *         sale.
     */
    public RunningDisplayInformationDTO updateItmInSale(int inputID) {
        Item itm = recrdItms.increaseQuantityOfAlreadyRecordItem(inputID);
        updateRunningTotalInclVAT(itm.getPrice(), itm.getVAT());
        updateVATEntireSale(itm.getVAT());

        return new RunningDisplayInformationDTO(itm, runningTotalInclVAT);
    }

    private double updateRunningTotalInclVAT(double itmPrice, double itmVAT) {
        double priceToAdd = itmPrice * (1 + itmVAT / 100);
        runningTotalInclVAT = runningTotalInclVAT + priceToAdd;

        return runningTotalInclVAT;
    }

    private void updateVATEntireSale(double VATToAdd) {
        VATEntireSale = VATEntireSale + VATToAdd;
    }

    /**
     * Ends the sale and returns the total price including VAT.
     * 
     * @return Total price including VAT.
     */
    public double endSale() {
        List<Item> collOfRecItms = recrdItms.getCollectionOfRecordedItems();
        setListOfBoughtItms(collOfRecItms);
        totalPriceInclVAT = runningTotalInclVAT;
        notifyObservers();

        return totalPriceInclVAT;
    }

    /**
     * Processes the payment for the sale.
     * 
     * @param amountPaid Amount paid by the customer.
     * @return Receipt information which should be given to the customer.
     */
    public ReceiptDTO processPayment(double amountPaid) {
        payment = new Payment(totalPriceInclVAT, VATEntireSale, amountPaid);
        sendSaleInfoToExternalSystems();

        return new ReceiptDTO(boghtItms, payment);
    }

    private void sendSaleInfoToExternalSystems() {
        SaleDTO saleDTO = new SaleDTO(this);
        accServ.sendSaleInfoToExternalAccountingSystem(saleDTO);
        invServ.sendSaleInfoToExternalInventorySystem(saleDTO);
    }

    private void setListOfBoughtItms(List<Item> soldItms) {
        for (Item itm : soldItms) {
            boghtItms.add(new BoughtItemDTO(itm));
        }
    }

    /**
     * Returns the list of all items which have been recorded in the sale once the
     * sale has ended.
     * 
     * @return List of all bought items.
     */
    public List<BoughtItemDTO> getListOfBoughtItems() {
        return boghtItms;
    }

    /**
     * Adds observers which should be notified when the running total has been
     * modified to this sale.
     * 
     * @param ob1 First observer to add in the list of who should be notified.
     * @param ob2 Second observer to add in the list of who should be notified.
     */

    public void addObserver(TemplateClass ob1, TemplateClass ob2) {
        saleObservers.add(ob1);
        saleObservers.add(ob2);
    }

    private void notifyObservers() {
        for (TemplateClass observer : saleObservers) {
            observer.newSaleWasMade(totalPriceInclVAT);
        }
    }

}
