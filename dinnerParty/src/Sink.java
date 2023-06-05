import java.util.Stack;

/**
 * Represents a sink with the inheritance of the inbuilt stack functionality.
 */
public class Sink extends Stack<Plate> {
    private TextPrinter printer;

    /**
     * Instansiates the sink.
     * 
     * @param printer Used to print out text to show the flow of the
     *                program.
     */
    public Sink(TextPrinter printer) {
        this.printer = printer;
    }

    /**
     * A plate is being placed in the sink.
     * 
     * @param currentPlate the dirty plate to place in the sink.
     */
    public void leavePlateInSink(Plate currentPlate) {
        this.push(currentPlate);
    }

    /**
     * Returns the number of dirty plates in the sink.
     * 
     * @return The number of dirty plates in the sink.
     */
    public int getAmountOfDirtyPlatesInSink() {
        return this.size();
    }

    /**
     * Shows if all plates are washed or not in the sink.
     * 
     * @return Boolean value showing if the sink is empty or not. If it is empty no
     *         dirty dishes are in the sink.
     */
    public boolean getIfSinkIsEmpty() {
        return this.empty();
    }

    /**
     * Process of when the sink is being cleaned.
     */
    public void emptyTheSink() {
        printer.startEmptyingSink();
        while (!this.empty()) {
            washPlate(this.firstElement());
        }
        printer.completedDishing();
    }

    private void washPlate(Plate plateToDish) {
        this.pop();
        printer.writeCountDirtyPlatesLeft(this.size());
    }
}
