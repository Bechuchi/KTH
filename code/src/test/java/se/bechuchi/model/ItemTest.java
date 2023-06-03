package se.bechuchi.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import se.bechuchi.integration.InventoryService;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.view.TotalRevenueFileOutput;

public class ItemTest {
    private TotalRevenueFileOutput logger;
    private ItemDTO itmDTO;
    private Item itm;
    private InventoryService invServ;
    private final int ITEM_IDENTIFIER = 2;

    @Before
    public void setUp() throws InvalidItemIdentifierException, DatabaseFailureException {
        invServ = InventoryService.getInstance();
        itmDTO = invServ.getItemDTO(ITEM_IDENTIFIER);
        itm = new Item(itmDTO);
    }

    @After
    public void tearDown() {
        invServ = null;
        itmDTO = null;
        itm = null;
    }

    @Test
    public void testGetItemDescription() {
        itm.increaseSoldQuantity(3);
        String expectedDescription = itm.getName() + "\t\t$" + itm.getPrice() + " * " + itm.getQuantity();
        String description = itm.getFormattedItemDescription();

        Assert.assertEquals(expectedDescription, description);
    }

    @Test
    public void testIncreaseSoldQuantity() {
        int qutyBfr = itm.getQuantity();
        itm.increaseSoldQuantity(2);
        int qutyAft = itm.getQuantity();

        boolean expectedResult = (qutyBfr == (qutyAft - 2));
        assertTrue(expectedResult);
    }
}
