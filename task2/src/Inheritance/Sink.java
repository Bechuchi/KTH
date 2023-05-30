package Inheritance;

import java.util.Stack;

public class Sink extends Stack<Plate> {
    private TextPrinter printer;

    public Sink(TextPrinter printer) {
        this.printer = printer;
    }

    public void leavePlateInSink(Plate currentPlate) {
        this.push(currentPlate);
    }

    public int getAmountOfDirtyPlatesInSink() {
        return this.size();
    }

    public boolean getIfSinkIsEmpty() {
        return this.empty();
    }

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
