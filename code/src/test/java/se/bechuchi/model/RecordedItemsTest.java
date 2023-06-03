package se.bechuchi.model;

import org.junit.Assert;

import java.util.List;

import org.junit.*;
import org.junit.Test;

import se.bechuchi.integration.InventoryService;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.view.FileLogger;
import se.bechuchi.view.TotalRevenueFileOutput;
import se.bechuchi.model.dto.ItemDTO;

public class RecordedItemsTest {
    private FileLogger logger;
    private TotalRevenueFileOutput totRvnFleOtpt;
    private RecordedItems recordedItems;
    private ItemDTO itmDTO;
    private Item itm;
    private InventoryService invServ;
    private final int ITEM_IDENTIFIER = 1;

    @Before
    public void setUp() throws InvalidItemIdentifierException, DatabaseFailureException {
        recordedItems = new RecordedItems();
        logger = new FileLogger();
        totRvnFleOtpt = new TotalRevenueFileOutput(logger);
        invServ = InventoryService.getInstance();
        itmDTO = new ItemDTO(1, "Item 1", 10.0, 1.2);
        itm = new Item(itmDTO);
        recordedItems = new RecordedItems();
    }

    @After
    public void tearDown() {
        itmDTO = null;
        itm = null;
        invServ = null;
        recordedItems = null;
    }

    @Test
    public void testIsThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier() {
        recordedItems.addNewItmToListOfRecordedItems(itmDTO);
        int nonExistingItmID = 100;

        boolean isExistingItmFound = recordedItems
                .isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(ITEM_IDENTIFIER);
        boolean isNonExistingItmFound = recordedItems
                .isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(nonExistingItmID);

        Assert.assertTrue(isExistingItmFound);
        Assert.assertFalse(isNonExistingItmFound);
    }

    @Test
    public void testAddNewItmToListOfRecordedItems() {
        recordedItems.addNewItmToListOfRecordedItems(itmDTO);

        List<Item> soldItemDTOs = recordedItems.getCollectionOfRecordedItems();
        boolean isNewItmFound = recordedItems
                .isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(itmDTO.getID());

        Assert.assertEquals(1, soldItemDTOs.size());
        Assert.assertTrue(isNewItmFound);
    }

    @Test
    public void testIncreaseQuantityOfAlreadyRecordItem1() {
        recordedItems.addNewItmToListOfRecordedItems(itmDTO);

        List<Item> soldItemDTOs = recordedItems.getCollectionOfRecordedItems();
        boolean isNewItmFound = recordedItems
                .isThereAnAlreadyRecordedItemMatchingCurrentItemIdentifier(itmDTO.getID());

        Assert.assertEquals(1, soldItemDTOs.size());
        Assert.assertTrue(isNewItmFound);
    }

    @Test
    public void testIncreaseQuantityOfAlreadyRecordItem() {
        recordedItems.increaseQuantityOfAlreadyRecordItem(itm.getID());
        Assert.assertEquals(1, itm.getQuantity());
    }
}
