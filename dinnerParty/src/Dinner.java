import java.util.ArrayList;
import java.util.List;

public class Dinner {
    private Sink sink;
    // private Zink sink;
    private TextPrinter printer;
    private int amountOfPlates;
    private boolean isKitchenClean;
    private List<Plate> platesOnTable;

    /*
     * public Dinner(Sink customStack, TextPrinter printer, int
     * amountOfExpectedGuests) {
     * this.sink = customStack;
     * this.printer = printer;
     * this.amountOfPlates = amountOfExpectedGuests + 1;
     * setKitchenPreparations();
     * }
     */

    public Dinner(Sink customStack, TextPrinter printer, int amountOfExpectedGuests) {
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

    public void everyoneLeavesTheTable() {
        for (Plate dirtyPlate : platesOnTable) {
            sink.leavePlateInSink(dirtyPlate);
            printer.writePlateDropInSink(dirtyPlate.getID());
        }
        System.out.println();
    }
}
