/*
 * Ansvarar för att skapa meddelanden.
    Denna klass ska ta hand om att generera de 10 viktvärdena och skapa meddelanden som inkluderar viktdata och ett unikt PaketID.
    Skapar meddelanden med viktvärden och PaketID:
*/

import java.net.InetAddress;
import java.util.Random;

public class MessageCreator {
    private static final Random random = new Random();

    // Skapar ett meddelande med 10 viktvärden och ett unikt PaketID
    public static String createMessage(int clientId) {
        double[] weightValues = generateWeightValues();
        int packetId = random.nextInt(10000); // Generera ett unikt PaketID

        // Skapa en JSON-sträng som representerar meddelandet
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append("\"PacketID\": \"").append(packetId).append("\", ");
        message.append("\"Weight\": \"");

        // Lägg till de 10 viktvärdena i JSON-strängen
        for (int i = 0; i < weightValues.length; i++) {
            message.append(weightValues[i]);
            if (i < weightValues.length - 1) {
                message.append(",");
            }
        }
        message.append("\"}");
        return message.toString();
    }

    // Genererar en array med 10 slumpmässiga viktvärden
    private static double[] generateWeightValues() {
        double[] weights = new double[10];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 10 + (50 - 10) * random.nextDouble(); // Vikter mellan 10 och 50
        }
        return weights;
    }
}
