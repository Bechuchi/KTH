package se.bechuchi.view;

import java.time.format.DateTimeFormatter;

import se.bechuchi.controller.Controller;
import se.bechuchi.integration.exception.DatabaseFailureException;
import se.bechuchi.integration.exception.InvalidItemIdentifierException;
import se.bechuchi.model.dto.RunningDisplayInformationDTO;
import se.bechuchi.view.exception.ExceptionFormatter;
import se.bechuchi.model.dto.BoughtItemDTO;
import se.bechuchi.model.dto.ReceiptDTO;

/**
 * Represents the public interface where the process sale is started and then a
 * set of data is being mocked. At the end the sale is automated ended as well.
 */
public class View {
    private Controller contr;
    private FileLogger logger;
    private ExceptionFormatter exepHndlr;

    /**
     * Creates an instance of the View with the Controller as an incoming paratmeter
     * in order to be able to communciate with lower layers in the programs
     * architechture. All exception information is logged and found in
     * errorReport.txt.
     * 
     * @param contr Controller used to communicate with other layers in the program.
     * @throws InvalidItemIdentifierException Occurs if the input value of the item
     *                                        identifier is invalid.
     * @throws DatabaseFailureException       Occurs when the connection to the
     *                                        external database is lost.
     */
    public View(Controller contr, FileLogger logger) {
        setup(contr, logger);

        for (int i = 0; i < 3; i++) {
            try {
                contr.startSale();
                switch (i) {
                    case 0:
                        System.out.println("Basic Flow Execution");
                        System.out.println("**************************");
                        mockMultipleItemScanWithRepeatedItemIdentifier();
                        break;
                    case 1:
                        System.out.println("Invalid Identifier Execution");
                        System.out.println("**************************");
                        mockInvalidItemIdentifierScan();
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("Database Failure Execution");
                        System.out.println("**************************");
                        mockDatabaseFailure();
                    default:
                        System.out.println("Error.");
                }
                double totalPrice = endSale();

                boolean discount = true;
                if (discount == true) {
                    int customerID = 1957;
                    totalPrice = contr.discountRequest(customerID, totalPrice);
                    System.out.println("Total price after discount: " + totalPrice);
                    System.out.println();
                }
                enterAmountPaid(30.0);
            } catch (InvalidItemIdentifierException | DatabaseFailureException e) {
                exepHndlr.printUserExceptionMessage(e);
                String fmattdDevExcMsg = exepHndlr.getDeveloperExceptionMessage(e);
                logger.logError(fmattdDevExcMsg);
            }
        }
    }

    private void setup(Controller contr, FileLogger logger) {
        this.exepHndlr = new ExceptionFormatter();
        this.contr = contr;
        this.logger = logger;
    }

    private void mockOneItemScan(int inputID) throws InvalidItemIdentifierException,
            DatabaseFailureException {
        RunningDisplayInformationDTO runnDispInfo = contr.scanItem(inputID);
        printRunningDisplayInformation(runnDispInfo.getRunningDisplayInformation());
    }

    private void printRunningDisplayInformation(String text) {
        System.out.println(text + "\n");
    }

    private void mockMultipleItemScanWithRepeatedItemIdentifier()
            throws InvalidItemIdentifierException, DatabaseFailureException {
        mockOneItemScan(1);
        for (int i = 0; i < 4; i++) {
            mockOneItemScan(i);
        }
        mockOneItemScan(2);
    }

    private void mockInvalidItemIdentifierScan() throws InvalidItemIdentifierException,
            DatabaseFailureException {
        mockOneItemScan(100);
    }

    private void mockDatabaseFailure() throws InvalidItemIdentifierException,
            DatabaseFailureException {
        mockOneItemScan(13);
    }

    private double endSale() {
        return contr.endSale();
    }

    private void enterAmountPaid(double amtPaid) {
        ReceiptDTO receDTO = contr.enterAmountPaid(amtPaid);
        printReceiptInformation(receDTO);
    }

    private void printReceiptInformation(ReceiptDTO receDTO) {
        System.out.println("RECEIPT");
        System.out.println("****************");
        printDateTime(receDTO);
        System.out.println();
        for (BoughtItemDTO itm : receDTO.getBoughtItems()) {
            System.out.println(itm.getBoughtItmDescription());
        }
        System.out.println();
        System.out.println("VAT:\t\t" + receDTO.getVATEntireSale());
        System.out.println("Amount paid:\t$" + receDTO.getAmountPaid());
        System.out.println("Change:\t\t$" + receDTO.getChange());
        System.out.println("Total price:\t$" + receDTO.getTotalPriceEntireSale());
        System.out.println();
    }

    private void printDateTime(ReceiptDTO receDTO) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Time: " + receDTO.getTime().format(timeFormatter));
        System.out.println("Date: " + receDTO.getDate().format(dateFormatter));
    }
}
