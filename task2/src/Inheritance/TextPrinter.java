package Inheritance;

public class TextPrinter {
    public void writeSetupQuestions(boolean isKitchenClean) {
        System.out.println("---------------------");
        System.out.println("Is the kitchen clean?\t\t\t" + isKitchenClean);
        System.out.println("So there is no dishes in the sink?\t" + isKitchenClean);
    }

    public void writeSetupAnswer(boolean isKitchenClean) {
        if (isKitchenClean == true) {
            System.out.println("Great, it is nice to have a clean kitchen when the guests arrives!");
        } else {
            System.out.println("Start dishing!");
        }

        System.out.println("---------------------");
        System.out.println();
    }

    public void writeArrivalText() {
        System.out.println("The guests are arriving....");
        System.out.println("...and a dinner started for 5 people");
        System.out.println();
    }

    public void writeDinnerEndingText() {
        System.out.println("---------------------");
        System.out.println(
                "Everyone is full and happy! All guests are leaving the table."
                        + "\nThey are very helpful and take their dirty plates to the sink.");
        System.out.println("---------------------");
        System.out.println();
    }

    public void writePlateDropInSink(int plateID) {
        System.out.println("Person with plate " + plateID + " just left the table and added the dirty plate"
                + " in the sink.");
    }

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

    public void writeGoodMorningMessage(int amountOfPlatesToWash) {
        System.out.println("Good morning!");
        System.out.println("A new day has come and its time for some dishing....");
        writeSinkCheck(amountOfPlatesToWash);
        System.out.println();
    }

    public void startEmptyingSink() {
        System.out.println("---------------------");
        System.out.println("Starting to empty the sink.");
        System.out.println();
    }

    public void writeCountDirtyPlatesLeft(int amountOfDirtyPlatesInSink) {
        System.out.println("Now there are only " + amountOfDirtyPlatesInSink + " left....");
    }

    public void completedDishing() {
        System.out.println("---------------------");
        System.out.println();
        System.out.println("Everything is clean!");
    }
}
