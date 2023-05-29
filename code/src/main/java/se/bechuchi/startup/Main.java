package se.bechuchi.startup;

import java.util.Stack;

import se.bechuchi.controller.Controller;
import se.bechuchi.integration.AccountingService;
import se.bechuchi.integration.DiscountService;
import se.bechuchi.integration.InventoryService;
import se.bechuchi.view.FileLogger;
import se.bechuchi.view.TotalRevenueFileOutput;
import se.bechuchi.view.TotalRevenvueView;
import se.bechuchi.view.View;

/**
 * Starts up the program and initializes everything nesseccary in order to make
 * the program run.
 */
public class Main {

    /**
     * The first method to be executed in the program.
     * 
     * @param args Optinonal in parameters. In this program no indata is required.
     */
    /*
     * public static void main(String[] args) {
     * FileLogger logger = new FileLogger();
     * Controller contr = new Controller(InventoryService.getInstance(), new
     * AccountingService(),
     * new DiscountService(), new TotalRevenvueView(),
     * new TotalRevenueFileOutput(logger));
     * new View(contr, logger);
     * }
     */

    public static void main(String[] args) {
        Inheritance customStack = new Inheritance();
        customStack.printStackSize();
        customStack.push(10);
        customStack.push(20);

        System.out.println("Is the stack empty? " + customStack.empty());
        customStack.printStackSize();

        Integer poppedElement = customStack.pop();
        System.out.println("Popped element: " + poppedElement);

        customStack.printStackSize();
    }
}
