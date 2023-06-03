package se.bechuchi.controller;

import java.util.List;

import se.bechuchi.integration.AccountingService;
import se.bechuchi.integration.DiscountService;
import se.bechuchi.integration.InventoryService;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.model.Sale;
import se.bechuchi.model.TemplateClass;
import se.bechuchi.model.disount.*;
import se.bechuchi.model.dto.BoughtItemDTO;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.model.dto.ReceiptDTO;
import se.bechuchi.view.TotalRevenueFileOutput;
import se.bechuchi.view.TotalRevenvueView;
import se.bechuchi.model.dto.RunningDisplayInformationDTO;

/**
 * The controller of the process sale. It is the only controller of this program
 * and is in charge of
 * directing all system calls from the view and returning the appropriate
 * response to it.
 */
public class Controller {
    private InventoryService invServ;
    private AccountingService accServ;
    private DiscountService discServ;
    private TemplateClass totRevenViw;
    private TemplateClass totRevenFleOtpt;
    private Sale sale;

    /**
     * Creates a new instance of the Controller.
     * 
     * @param invServ The external inventory service connecting to the external
     *                inventory system.
     * @param accServ The external accounting service connecting to the external
     *                accounting system..
     */
    public Controller(InventoryService invServ, AccountingService accServ, DiscountService discServ,
            TotalRevenvueView totRevenViw, TotalRevenueFileOutput totRevenFleOtpt) {
        this.invServ = invServ;
        this.accServ = accServ;
        this.discServ = discServ;
        this.totRevenViw = totRevenViw;
        this.totRevenFleOtpt = totRevenFleOtpt;
    }

    /**
     * Starts a new sale.
     */
    public void startSale() {
        sale = new Sale(invServ, accServ);
        sale.addObserver(totRevenViw, totRevenFleOtpt);
    }

    /**
     * Scans an item in the sale and checks if the specified item identifier already
     * has been recorded into the
     * sale.
     * If the specified input identifier is not recognized by the system, a new
     * instance will be created for
     * this item. The inventory system will be called to create this new instance.
     * 
     * If the specified input identifier is recognized, then information to this
     * belonging
     * item will be updated.
     * 
     * @param inputID The value of the identifier which has been scanned by the
     *                cashier.
     * @return The information to publicly display.
     * @throws InvalidItemIdentifierException Occurs if the input value of the item
     *                                        identifier is invalid.
     */
    public RunningDisplayInformationDTO scanItem(int inputID)
            throws InvalidItemIdentifierException, DatabaseFailureException {
        boolean itmFound = sale.hasIdentifierBeenRecordBefore(inputID);
        if (!itmFound) {
            ItemDTO itmDTO = invServ.getItemDTO(inputID);
            return sale.addNewItmToSale(itmDTO);
        } else {
            return sale.updateItmInSale(inputID);
        }
    }

    /**
     * Ends the sale when the customer don't want to buy anything more.
     * 
     * @return The total price including VAT.
     */
    public double endSale() {
        double totalPriceWithoutDiscount = sale.endSale();

        return totalPriceWithoutDiscount;
    }

    /**
     * Performs a discount request in the process sale. It is possible to have
     * discount based on being a retiree,
     * or also having a premium type of discount. Adopted with this there are also
     * variations of items giving
     * dicount values.
     * 
     * @param customerID               Identification value of the customer
     *                                 requesting to get a discount.
     * @param totalPriceBeforeDiscount The price of the sale before possible
     *                                 discount has been added.
     * @return The total price with the discount value in the calculation.
     */
    public double discountRequest(int customerID, double totalPriceBeforeDiscount) {
        boolean customerIsEligibleForDiscount = discServ.isCustomerElibibleForDiscount(customerID);

        if (customerIsEligibleForDiscount) {
            List<BoughtItemDTO> boughtItems = sale.getListOfBoughtItems();
            ItemBehavior itemBehavior = new ItemQuantity();
            CustomerBehavior customerBehavior = null;

            if (discServ.isCustomerEligibleForRetireeDiscount(customerID)) {
                customerBehavior = new RetireeCustomer();
            } else if (discServ.isCustomerEligibleForPremiumDiscount(customerID)) {
                customerBehavior = new PremiumCustomer();
            }

            Discount discount = new SpecificDiscount(totalPriceBeforeDiscount, itemBehavior, customerBehavior,
                    customerID, discServ);
            double totalPriceAfterDiscount = discount.calculateFinalDiscountValue(totalPriceBeforeDiscount, customerID,
                    boughtItems);
            return totalPriceAfterDiscount;
        }

        return totalPriceBeforeDiscount;
    }

    /**
     * Registers the amount paid by the customer and returns the receipt
     * information.
     * 
     * @param amtPaid The amount paid by the customer after all items have been
     *                scanned.
     * @return The receipt information which should be given to the customer at the
     *         end of the sale.
     */
    public ReceiptDTO enterAmountPaid(double amtPaid) {
        return sale.processPayment(amtPaid);
    }
}
