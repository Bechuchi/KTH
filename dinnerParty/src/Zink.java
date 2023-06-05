import java.util.Stack;

public class Zink {
    private Stack<Plate> platesInSink;
    private TextPrinter printer;

    public Zink(TextPrinter printer) {
        this.platesInSink = new Stack<Plate>();
        this.printer = printer;
    }

    public void leavePlateInSink(Plate plate) {
        platesInSink.push(plate);
    }

    public int getAmountOfDirtyPlatesInSink() {
        return platesInSink.size();
    }

    public boolean getIfSinkIsEmpty() {
        return platesInSink.empty();
    }

    private void washPlate(Plate plateToDish) {
        platesInSink.pop();
        printer.writeCountDirtyPlatesLeft(platesInSink.size());
    }

    public void emptyTheSink() {
        printer.startEmptyingSink();
        while (!platesInSink.empty()) {
            washPlate(platesInSink.firstElement());
        }
        printer.completedDishing();
    }
}
