package se.bechuchi.controller;

import org.junit.*;

import se.bechuchi.integration.AccountingService;
import se.bechuchi.integration.InventoryService;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.model.Sale;
import se.bechuchi.view.FileLogger;
import se.bechuchi.view.TotalRevenueFileOutput;

public class ControllerTest {
    private FileLogger logger;
    private TotalRevenueFileOutput totRvnFleOtpt;
    private InventoryService invServ;
    private AccountingService accServ;
    private Sale sale;

    @Before
    public void setUp() throws DatabaseFailureException {
        logger = new FileLogger();
        totRvnFleOtpt = new TotalRevenueFileOutput(logger);
        invServ = InventoryService.getInstance();
        accServ = new AccountingService();
        sale = new Sale(invServ, accServ);
    }

    @After
    public void tearDown() {
        invServ = null;
        accServ = null;
        sale = null;
    }
}
