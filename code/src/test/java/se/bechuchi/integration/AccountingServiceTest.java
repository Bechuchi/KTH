package se.bechuchi.integration;

import org.junit.*;

import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.model.Sale;
import se.bechuchi.model.dto.SaleDTO;
import se.bechuchi.view.TotalRevenueFileOutput;

public class AccountingServiceTest {
    private TotalRevenueFileOutput logger;
    private InventoryService invServ;
    private AccountingService accServ;
    private Sale sale;
    private SaleDTO saleDTO;

    @Before
    public void setUp() throws DatabaseFailureException {
        invServ = InventoryService.getInstance();
        accServ = new AccountingService();
        sale = new Sale(invServ, accServ);
    }

    @After
    public void tearDown() {
        invServ = null;
        accServ = null;
        sale = null;
        saleDTO = null;
    }

    @Test
    public void testSendSaleInfoToExternalAccountingSystem() {
        SaleDTO expectedSaleDTO = new SaleDTO(sale);

        // Call the method
        accServ.sendSaleInfoToExternalAccountingSystem(expectedSaleDTO);

        // Assert that the saleDTO object is correctly assigned
        Assert.assertEquals(expectedSaleDTO, accServ.getSaleDTO());
    }
}
