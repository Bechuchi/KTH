package se.bechuchi.integration;

import org.junit.*;

import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.model.Sale;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.model.dto.SaleDTO;

public class InventoryServiceTest {
    private AccountingService accServ;
    private InventoryService invServ;
    private Sale sale;
    private final int ITEM_DTO_IDENTIFIER = 0;
    private final String ITEM_DTO_NAME = "Milk";

    @Before
    public void setUp() throws DatabaseFailureException {
        accServ = new AccountingService();
        invServ = InventoryService.getInstance();
        sale = new Sale(invServ, accServ);
    }

    @After
    public void tearDown() {
        invServ = null;
        accServ = null;
        sale = null;
    }

    @Test
    public void testGetInstance() {
        InventoryService instance1 = InventoryService.getInstance();
        InventoryService instance2 = InventoryService.getInstance();

        Assert.assertSame(instance1, instance2);
    }

    @Test
    public void testGetItemDTO() throws InvalidItemIdentifierException, DatabaseFailureException {
        ItemDTO result = invServ.getItemDTO(ITEM_DTO_IDENTIFIER);

        Assert.assertEquals(ITEM_DTO_IDENTIFIER, result.getID());
        Assert.assertEquals(ITEM_DTO_NAME, result.getName());
    }

    @Test
    public void testSendSaleInfoToExternalInventorySystem() {
        SaleDTO expectedSaleDTO = new SaleDTO(sale);
        invServ.sendSaleInfoToExternalInventorySystem(expectedSaleDTO);
        Assert.assertEquals(expectedSaleDTO, invServ.getSaleDTO());
    }
}
