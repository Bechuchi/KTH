package se.bechuchi.model.disount;

import java.util.List;

import se.bechuchi.integration.DiscountService;
import se.bechuchi.model.dto.BoughtItemDTO;

/**
 * A concrete representation of the abstact class Discount.
 */
public class SpecificDiscount extends Discount {
    /**
     * Creates a new instance of this concrete class when dicount calculations
     * should be made.
     * 
     * @param totalPriceBeforeDiscount Total price of process sale before discount
     *                                 has been added.
     * @param itemBehavior             Common algorithms for how bought items can be
     *                                 handled in a discount request.
     * @param customerBehavior         Common algorithms for how the customer for
     *                                 whom the discount request is being made for
     *                                 can be handeled.
     * @param customerID               Identification value of the customer.
     * @param discServ                 Discount service responsible for connecting
     *                                 the external dicount database.
     */
    public SpecificDiscount(double totalPriceBeforeDiscount, ItemBehavior itemBehavior,
            CustomerBehavior customerBehavior,
            int customerID, DiscountService discServ) {
        super(totalPriceBeforeDiscount, itemBehavior, customerBehavior, customerID, discServ);
    }

    /**
     * Calculates the final discount value.
     */
    @Override
    public double calculateFinalDiscountValue(double totalPriceBeforeDiscunt, int customerID,
            List<BoughtItemDTO> purchasedItems) {
        return 0.90 * totalPriceBeforeDiscunt;
    }

}
