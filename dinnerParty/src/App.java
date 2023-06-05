/**
 * This is the class starting the execution of this program. It initializes a
 * dinner party that is being ended
 * with a scenario of cleaning dirty plates in a sink. It demonstrates
 * Inheritance and Composition. To see the
 * different implementations the instance of the custom stack needs to be
 * commented differently. This part is
 * also affected in the Dinner class.
 */
public class App {
    /**
     * Starts the execution of this program.
     * 
     * @param args No input arguments are required.
     * @throws Exception No exception handling is made for this project.
     */
    public static void main(String[] args) throws Exception {
        TextPrinter textPrinter = new TextPrinter();
        // Sink customStack = new Sink(textPrinter);
        Zink customStack = new Zink(textPrinter);
        int amountOfExpectedGuests = 5;

        Dinner friendsMeetup = new Dinner(customStack, textPrinter, amountOfExpectedGuests);
        textPrinter.writeArrivalText();
        textPrinter.writeDinnerEndingText();
        friendsMeetup.everyoneLeavesTheTable();
        textPrinter.writeEveningDeceleration(customStack.getAmountOfDirtyPlatesInSink());
        textPrinter.writeGoodMorningMessage(customStack.getAmountOfDirtyPlatesInSink());
        customStack.emptyTheSink();
    }
}
