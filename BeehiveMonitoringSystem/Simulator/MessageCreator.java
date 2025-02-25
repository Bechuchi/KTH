/*
 * Ansvarar för att skapa meddelanden.
    Denna klass ska ta hand om att generera de 10 viktvärdena och skapa meddelanden som inkluderar viktdata och ett unikt PaketID.
    Skapar meddelanden med viktvärden och PaketID:
*/

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageCreator {
    private static final Random random = new Random();

    // Skapar ett meddelande med 10 viktvärden och ett unikt PaketID

    public static String createMessage(int clientID) {
        double[] weightValues = generateWeightValues();
        String IPAddress = "192.168.137.x";
        String MACAddress = "1bb2.168.137.Y";
        int packetId = random.nextInt(10000); // Unikt paket-ID

        // Skapa JSON-struktur som en sträng
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append("\"ipAddress\": \"").append(IPAddress).append("\", ");
        message.append("\"macAddress\": \"").append(MACAddress).append("\", ");
        message.append("\"weightValues\": [");

        // Lägg till viktvärden
        for (int i = 0; i < weightValues.length; i++) {
            message.append(weightValues[i]);
            if (i < weightValues.length - 1) {
                message.append(", ");
            }
        }

        message.append("]}"); // Stäng JSON-objektet

        return message.toString();
    }

    public static String createMessageTest(int clientId) {
        double[] weightValues = generateWeightValues();
        String IPaddress = "192.168.137.X";
        String MACaddress = "1bb2.168.137.Y";
        int packetId = random.nextInt(10000); // Generera ett unikt PaketID

        // Skapa en JSON-sträng som representerar meddelandet
        StringBuilder message = new StringBuilder();
        message.append("{");
        // message.append("\"PacketID\": \"").append(packetId).append("\", ");
        message.append("\"ipAddress\": \"").append(IPaddress).append("\", ");
        message.append("\"macAddress\": \"").append(MACaddress).append("\", ");
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
