import java.util.Stack;

/**
 * Represents a sink with composition of the inbuilt stack functionality.
 */
public class Zink {
    private Stack<Plate> platesInSink;
    private TextPrinter printer;

    /**
     * Instansiates the sink.
     * 
     * @param printer Used to print out text to show the flow of the
     *                program.
     */
    public Zink(TextPrinter printer) {
        this.platesInSink = new Stack<Plate>();
        this.printer = printer;
    }

    /**
     * A plate is being placed in the sink.
     * 
     * @param plate the dirty plate to place in the sink.
     */
    public void leavePlateInSink(Plate plate) {
        platesInSink.push(plate);
    }

    /**
     * Returns the number of dirty plates in the sink.
     * 
     * @return The number of dirty plates in the sink.
     */
    public int getAmountOfDirtyPlatesInSink() {
        return platesInSink.size();
    }

    /**
     * Shows if all plates are washed or not in the sink.
     * 
     * @return Boolean value showing if the sink is empty or not. If it is empty no
     *         dirty dishes are in the sink.
     */
    public boolean getIfSinkIsEmpty() {
        return platesInSink.empty();
    }

    private void washPlate(Plate plateToDish) {
        platesInSink.pop();
        printer.writeCountDirtyPlatesLeft(platesInSink.size());
    }

    /**
     * Process of when the sink is being cleaned.
     */
    public void emptyTheSink() {
        printer.startEmptyingSink();
        while (!platesInSink.empty()) {
            washPlate(platesInSink.firstElement());
        }
        printer.completedDishing();
    }
}
