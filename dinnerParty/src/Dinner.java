import java.util.ArrayList;
import java.util.List;

/**
 * Represents the flow of a dinner party and different parts for demonstrating
 * how dirty plates are placed in the zink.
 */
public class Dinner {
    // private Sink sink;
    private Zink sink;
    private TextPrinter printer;
    private int amountOfPlates;
    private boolean isKitchenClean;
    private List<Plate> platesOnTable;

    /**
     * Instansiates the dinner, here with a custom stack demonstrating inheritance.
     * 
     * @param customStack            The sink which should be a place holder of
     *                               dirty plates to show how a stack works.
     * @param printer                Used to print out text to show the flow of the
     *                               program.
     * @param amountOfExpectedGuests Number of guests in the dinner party.
     */
    /*
     * public Dinner(Sink customStack, TextPrinter printer, int
     * amountOfExpectedGuests) {
     * this.sink = customStack;
     * this.printer = printer;
     * this.amountOfPlates = amountOfExpectedGuests + 1;
     * setKitchenPreparations();
     * }
     */

    /**
     * Instansiates the dinner, here with a custom stack demonstrating composition.
     * 
     * @param customStack            The sink which should be a place holder of
     *                               dirty plates to show how a stack works.
     * @param printer                Used to print out text to show the flow of the
     *                               program.
     * @param amountOfExpectedGuests Number of guests in the dinner party.
     */
    public Dinner(Zink customStack, TextPrinter printer, int amountOfExpectedGuests) {
        this.sink = customStack;
        this.printer = printer;
        this.amountOfPlates = amountOfExpectedGuests + 1;
        setKitchenPreparations();
    }

    private void setKitchenPreparations() {
        setIfKitchenIsClean();
        printer.writeSetupQuestions(isKitchenClean);
        printer.writeSetupAnswer(isKitchenClean);
        setTableWithCleanPlates();
    }

    private void setIfKitchenIsClean() {
        this.isKitchenClean = sink.getIfSinkIsEmpty();
    }

    private void setTableWithCleanPlates() {
        platesOnTable = new ArrayList<Plate>();

        for (var i = 0; i < amountOfPlates; i++) {
            platesOnTable.add(new Plate(i));
        }
    }

    /**
     * The process of when the guests are leaving the table and are placing their
     * dirty plates in the sink.
     */
    public void everyoneLeavesTheTable() {
        for (Plate dirtyPlate : platesOnTable) {
            sink.leavePlateInSink(dirtyPlate);
            printer.writePlateDropInSink(dirtyPlate.getID());
        }
        System.out.println();
    }
}
