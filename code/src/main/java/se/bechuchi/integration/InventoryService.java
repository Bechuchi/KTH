package se.bechuchi.integration;

import java.time.LocalTime;
import java.util.*;

import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.model.dto.ItemDTO;
import se.bechuchi.model.dto.SaleDTO;

/**
 * Represents the service between this program and the external inventory
 * system.
 */
public class InventoryService {
    private static InventoryService instance = null;
    public List<ItemDTO> mockedItemRegistry;
    private SaleDTO saleDTO;

    /**
     * Returns the singleton instance of the InventoryService class.
     * If the instance has not been created yet, it creates a new instance
     * and returns it. Subsequent calls to this method will always return
     * the same instance.
     *
     * @return The singleton instance of the InventoryService class.
     */
    public static synchronized InventoryService getInstance() {
        if (instance == null) {
            instance = new InventoryService();
        }

        return instance;
    }

    /**
     * Returns the specified itemDTO based on the value of the input item
     * identifier.
     * 
     * @param inputID Item identifier which should match the searched itemDTO.
     * @return The searched itemDTO
     * @throws InvalidItemIdentifierException Occurs if the input value of the item
     *                                        identifier is invalid.
     * @throws DatabaseFailureException       Occurs when the connection to the
     *                                        external database is lost.
     */
    public ItemDTO getItemDTO(int inputID) throws InvalidItemIdentifierException, DatabaseFailureException {
        return findItem(inputID);
    }

    /**
     * Sends sale information to the external inventory system.
     * 
     * @param saleDTO The sale information which should be send externally.
     */
    public void sendSaleInfoToExternalInventorySystem(SaleDTO saleDTO) {
        this.saleDTO = saleDTO;
    }

    /**
     * Returns the object saleDTO.
     * 
     * @return The object saleDTO which contains information that should be send
     *         externally.
     */
    public SaleDTO getSaleDTO() {
        return saleDTO;
    }

    /**
     * Creates a new instance of the inventory service.
     * Mocks a list of ItemDTOs which is the representation of all information about
     * an item
     * collected from the external inventory system.
     */
    private InventoryService() {
        mockedItemRegistry = new ArrayList<ItemDTO>();
        mockItemsInRegistry();
    }

    private void mockItemsInRegistry() {
        addItemToListOfItemsInRegistry(new ItemDTO(0, "Milk", 1.2, 0.25));
        addItemToListOfItemsInRegistry(new ItemDTO(1, "Butter", 3.2, 0.12));
        addItemToListOfItemsInRegistry(new ItemDTO(2, "Coffee", 4.3, 0.06));
        addItemToListOfItemsInRegistry(new ItemDTO(3, "Cream", 1.8, 0.25));
        addItemToListOfItemsInRegistry(new ItemDTO(4, "Juice", 1.6, 0.06));
        addItemToListOfItemsInRegistry(new ItemDTO(5, "Chicken", 7.8, 0.06));
        addItemToListOfItemsInRegistry(new ItemDTO(6, "Rice", 5.9, 0.12));
    }

    private void addItemToListOfItemsInRegistry(ItemDTO itmDTO) {
        mockedItemRegistry.add(itmDTO);
    }

    private ItemDTO findItem(int inputID) throws InvalidItemIdentifierException, DatabaseFailureException {
        for (int i = 0; i < mockedItemRegistry.size(); i++) {
            if (mockedItemRegistry.get(i).getID() == inputID) {
                return mockedItemRegistry.get(inputID);
            } else if (inputID == 13) {
                LocalTime timeStampOfExeption = LocalTime.now();
                throw new DatabaseFailureException(timeStampOfExeption);
            }
        }
        LocalTime timeStampOfExeption = LocalTime.now();

        throw new InvalidItemIdentifierException(inputID, timeStampOfExeption);
    }
}
