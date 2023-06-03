package se.bechuchi.model;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.bechuchi.integration.AccountingService;
import se.bechuchi.integration.InventoryService;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.view.FileLogger;
import se.bechuchi.view.TotalRevenueFileOutput;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.model.dto.RunningDisplayInformationDTO;

public class SaleTest {
    private FileLogger logger;
    private TotalRevenueFileOutput totRvnFleOtot;
    private Sale sale;
    private CollectionOfRecordedItems recrdItms;
    private final int ITEM_IDENTIFIER = 2;
    private InventoryService invServ;
    private AccountingService accServ;
    private ItemDTO itmDTO;
    private Item itm;

    @Before
    public void setUp() throws InvalidItemIdentifierException, DatabaseFailureException {
        logger = new FileLogger();
        totRvnFleOtot = new TotalRevenueFileOutput(logger);
        invServ = InventoryService.getInstance();
        accServ = new AccountingService();
        sale = new Sale(invServ, accServ);
        recrdItms = new CollectionOfRecordedItems();
        itmDTO = invServ.getItemDTO(ITEM_IDENTIFIER);
        itm = new Item(itmDTO);
    }

    @After
    public void tearDown() {
        invServ = null;
        accServ = null;
        sale = null;
        recrdItms = null;
        itmDTO = null;
        itm = null;
    }

    @Test
    public void testHasIdentifierBeenRecordBefore() {
        boolean result = sale.hasIdentifierBeenRecordBefore(ITEM_IDENTIFIER);
        assertFalse(result);

        sale.addNewItmToSale(itmDTO);
        result = sale.hasIdentifierBeenRecordBefore(ITEM_IDENTIFIER);
        assertTrue(result);
    }

    @Test
    public void testAddNewItmToSale() {
        Item newItm = recrdItms.addNewItmToListOfRecordedItems(itmDTO);
        double mockedRunningTotal = 0.0;
        RunningDisplayInformationDTO runnDTO = new RunningDisplayInformationDTO(newItm, mockedRunningTotal);
        double runningBefore = runnDTO.getRunningTotal();
        RunningDisplayInformationDTO runn2DTO = sale.addNewItmToSale(itmDTO);
        double runningAfter = runn2DTO.getRunningTotal();

        Assert.assertTrue(runningBefore < runningAfter);
    }

    @Test
    public void testIncreaseQuantityOfAlreadyRecordItem() {
        int qutyBfr = itm.getQuantity();
        sale.addNewItmToSale(itmDTO);
        sale.updateItmInSale(ITEM_IDENTIFIER);
        int qutyAft = itm.getQuantity();

        assertTrue(qutyBfr == (qutyAft - 1));
    }
}
