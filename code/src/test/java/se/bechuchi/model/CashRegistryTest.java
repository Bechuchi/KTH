package se.bechuchi.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Assert;

public class CashRegistryTest {
    private final double AMOUNT_PAID = 100.0;
    private CashRegistry cashReg;

    @Before
    public void setUp() {
        cashReg = new CashRegistry(AMOUNT_PAID);
    }

    @After
    public void tearDown() {
        cashReg = null;
    }

    @Test
    public void testCashRegistry()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Verify that the constructor assigns the correct value to the amountPaid field
        Field amountPaidField = CashRegistry.class.getDeclaredField("amountPaid");
        amountPaidField.setAccessible(true);
        double actualAmountPaid = amountPaidField.getDouble(cashReg);
        Assert.assertEquals(AMOUNT_PAID, actualAmountPaid, 0.01);

        // Verify that the constructor updates the amountPresent field correctly
        Field amountPresentField = CashRegistry.class.getDeclaredField("amountPresent");
        amountPresentField.setAccessible(true);
        double actualAmountPresent = amountPresentField.getDouble(cashReg);
        double expectedAmountPresent = 2600.0 + AMOUNT_PAID;
        Assert.assertEquals(expectedAmountPresent, actualAmountPresent, 0.01);
    }
}
