package se.bechuchi.model;

import org.junit.Assert;
import org.junit.Test;

public class PaymentTest {
    private final double TOTAL_PRICE_INCL_VAT = 40.0;
    private final double VAT_ENTIRE_SALE = 1.2;
    private final double AMOUNT_PAID = 38.0;

    @Test
    public void testPayment() {
        double expectedResult = AMOUNT_PAID - TOTAL_PRICE_INCL_VAT;
        Payment paym = new Payment(TOTAL_PRICE_INCL_VAT, VAT_ENTIRE_SALE, AMOUNT_PAID);
        double result = paym.getChange();

        Assert.assertTrue(expectedResult == result);
    }
}
