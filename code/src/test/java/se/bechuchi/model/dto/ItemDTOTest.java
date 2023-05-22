package se.bechuchi.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.*;

public class ItemDTOTest {
    private ItemDTO itmDTO;
    private final int ITEM_DTO_IDENTIFIER = 0;
    private final String ITEM_DTO_NAME = "Milk";
    private final double ITEM_DTO_PRICE = 12.0;
    private final double ITEM_DTO_VAT = 0.25;

    @Before
    public void setUp() {
        itmDTO = new ItemDTO(ITEM_DTO_IDENTIFIER, ITEM_DTO_NAME, ITEM_DTO_PRICE, ITEM_DTO_VAT);
    }

    @After
    public void tearDown() {
        itmDTO = null;
    }

    @Test
    public void testGetID() {
        int result = itmDTO.getID();
        int expectedResult = ITEM_DTO_IDENTIFIER;

        assertTrue(result == expectedResult);
    }

    @Test
    public void testGetName() {
        String result = itmDTO.getName();
        String expectedResult = ITEM_DTO_NAME;

        assertEquals(result, expectedResult);
    }

    @Test
    public void testGetPrice() {
        double result = itmDTO.getPrice();
        double expectedResult = ITEM_DTO_PRICE;

        assertTrue(result == expectedResult);
    }

    @Test
    public void testGetVAT() {
        double result = itmDTO.getVAT();
        double expectedResult = ITEM_DTO_VAT;

        assertTrue(result == expectedResult);
    }
}
