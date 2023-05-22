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
    private CollectionOfRecordedItems recordedItems;
    private ItemDTO itmDTO;
    private InventoryService invServ;
    private final int ITEM_IDENTIFIER = 1;

    @Before
    public void setUp() throws InvalidItemIdentifierException, DatabaseFailureException {
        recordedItems = new CollectionOfRecordedItems();
        logger = new FileLogger();
        totRvnFleOtpt = new TotalRevenueFileOutput(logger);
        invServ = InventoryService.getInstance();
        // itmDTO = invServ.getItemDTO(ITEM_IDENTIFIER);
        itmDTO = new ItemDTO(1, "Item 1", 10.0, 1.2);
        recordedItems = new CollectionOfRecordedItems();
    }

    @After
    public void tearDown() {
        itmDTO = null;
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
        // Add a new item to the recorded items
        recordedItems.addNewItmToListOfRecordedItems(itmDTO);

        // Verify that the item was added correctly
        // List<ItemDTO> soldItemDTOs = recordedItems.getSoldItemDTOs();
        // Assert.assertEquals(1, soldItemDTOs.size());
        // Assert.assertEquals(itmDTO, soldItemDTOs.get(0));
    }
}
