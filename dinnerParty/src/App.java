public class App {
    public static void main(String[] args) throws Exception {
        TextPrinter textPrinter = new TextPrinter();
        Sink customStack = new Sink(textPrinter);
        // Zink customStack = new Zink(textPrinter);
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
