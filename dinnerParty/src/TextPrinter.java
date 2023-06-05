/**
 * Used to print out information of the program execution.
 */
public class TextPrinter {
    /**
     * Prints the set up questions to see if the kitchen is clean.
     * 
     * @param isKitchenClean boolean value showing if the kitchen is clean or not.
     */
    public void writeSetupQuestions(boolean isKitchenClean) {
        System.out.println("---------------------");
        System.out.println("Is the kitchen clean?\t\t\t" + isKitchenClean);
        System.out.println("So there is no dishes in the sink?\t" + isKitchenClean);
    }

    /**
     * Prints the answer of if the kitchen is clean or not.
     * 
     * @param isKitchenClean boolean value showing if the kitchen is clean or not.
     */
    public void writeSetupAnswer(boolean isKitchenClean) {
        if (isKitchenClean == true) {
            System.out.println("Great, it is nice to have a clean kitchen when the guests arrives!");
        } else {
            System.out.println("Start dishing!");
        }

        System.out.println("---------------------");
        System.out.println();
    }

    /**
     * Prints the arrival text of when guests are coming.
     */
    public void writeArrivalText() {
        System.out.println("The guests are arriving....");
        System.out.println("...and a dinner started for 5 people");
        System.out.println();
    }

    /**
     * Prints the text to show that the dinner is ending.
     */
    public void writeDinnerEndingText() {
        System.out.println("---------------------");
        System.out.println(
                "Everyone is full and happy! All guests are leaving the table."
                        + "\nThey are very helpful and take their dirty plates to the sink.");
        System.out.println("---------------------");
        System.out.println();
    }

    /**
     * Prints how the dirty plate is being placed in the sink.
     * 
     * @param plateID
     */
    public void writePlateDropInSink(int plateID) {
        System.out.println("Person with plate " + plateID + " just left the table and added the dirty plate"
                + " in the sink.");
    }

    /**
     * Prints a summary of the evening status.
     * 
     * @param amountOfDirtyPlatesInSink Number of dirty plates in the sink.
     */
    public void writeEveningDeceleration(int amountOfDirtyPlatesInSink) {
        System.out.println("---------------------");
        System.out.println("The evening was super!");
        writeSinkCheck(amountOfDirtyPlatesInSink);
        System.out.println("That can wait for tomorrow.");
        System.out.println("---------------------");
        System.out.println();
    }

    private void writeSinkCheck(int amountOfDirtyPlatesInSink) {
        System.out.println("How many plates are there in the sink?\t" + amountOfDirtyPlatesInSink + " st.");
    }

    /**
     * Prints a goodmorning message and a check of the sink.
     * 
     * @param amountOfPlatesToWash
     */
    public void writeGoodMorningMessage(int amountOfPlatesToWash) {
        System.out.println("Good morning!");
        System.out.println("A new day has come and its time for some dishing....");
        writeSinkCheck(amountOfPlatesToWash);
        System.out.println();
    }

    /**
     * Prints the start of cleaning the sink.
     */
    public void startEmptyingSink() {
        System.out.println("---------------------");
        System.out.println("Starting to do the dishes......");
        System.out.println();
    }

    /**
     * Prints how many dirty plates are remaining in the sink.
     * 
     * @param amountOfDirtyPlatesInSink Number of dirty plates in the sink.
     */
    public void writeCountDirtyPlatesLeft(int amountOfDirtyPlatesInSink) {
        System.out.println("Now there are only " + amountOfDirtyPlatesInSink + " left....");
    }

    /**
     * Prints that the sink is clean.
     */
    public void completedDishing() {
        System.out.println("---------------------");
        System.out.println();
        System.out.println("Everything is clean!");
    }
}
