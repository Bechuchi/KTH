/*
 * Ansvarar för att skapa meddelanden.
    Denna klass ska ta hand om att generera de 10 viktvärdena och skapa meddelanden som inkluderar viktdata och ett unikt PaketID.
    Skapar meddelanden med viktvärden och PaketID:
*/

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageCreator {
    private static final Random random = new Random();

    // Skapar ett meddelande med 10 viktvärden och ett unikt PaketID

    public static String createMessage(int clientID) {
        List<Double> weightValues;

        // Klient 5 = "Slutat producera" (0 → 20 kg och avstannar)
        if (clientID == 5) {
            weightValues = generateStableWeights(20);
        }
        // Klienter 1-4 = "Optimal bikupa" (0 → 60 kg)
        else {
            weightValues = generateIncreasingWeights(60);
        }

        String IPAddress = "192.168.137." + clientID;
        String MACAddress = "1B:B2:16:8" + clientID + ":37:Z";
        int packetId = random.nextInt(10000);

        // Skapa JSON-struktur
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append("\"ipAddress\":\"").append(IPAddress).append("\", ");
        message.append("\"macAddress\":\"").append(MACAddress).append("\", ");
        message.append("\"weightValues\": [");

        for (int i = 0; i < weightValues.size(); i++) {
            message.append(weightValues.get(i));
            if (i < weightValues.size() - 1) {
                message.append(", ");
            }
        }

        message.append("]}");

        return message.toString();
    }

    public static String createMessageOld(int clientID) {
        List<Double> weightValues = generateWeightValues();
        String IPAddress = "192.168.137." + clientID; // Gör IP-adressen unik
        String MACAddress = "1B:B2:16:8" + clientID + ":37:Z"; // Unik MAC-adress
        int packetId = random.nextInt(10000); // Unikt paket-ID

        // Skapa JSON-struktur som en sträng
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append("\"ipAddress\": \"").append(IPAddress).append("\", ");
        message.append("\"macAddress\": \"").append(MACAddress).append("\", ");

        message.append("\"weightValues\": [");

        // Lägg till viktvärden
        for (int i = 0; i < weightValues.size(); i++) {
            message.append(weightValues.get(i)); // Korrekt sätt att hämta värden i en lista
            if (i < weightValues.size() - 1) {
                message.append(", "); // Komma mellan värdena
            }
        }

        message.append("]}"); // Stäng JSON-objektet

        return message.toString();
    }

    // Viktökning från 0 kg till maxVikt (för optimal bikupa)
    private static List<Double> generateIncreasingWeights(double maxVikt) {
        List<Double> weights = new ArrayList<>();
        double currentWeight = 0;
        for (int i = 0; i < 10; i++) {
            currentWeight += (maxVikt / 10) + random.nextDouble(); // Ökar stadigt
            weights.add(currentWeight);
        }
        return weights;
    }

    // Stabil vikt efter en viss ökning (för bikupa som slutat producera)
    private static List<Double> generateStableWeights(double maxVikt) {
        List<Double> weights = new ArrayList<>();
        double currentWeight = 0;
        for (int i = 0; i < 5; i++) { // Ökar till maxVikt
            currentWeight += (maxVikt / 5) + random.nextDouble();
            weights.add(currentWeight);
        }
        for (int i = 5; i < 10; i++) { // Stannar på maxVikt
            weights.add(maxVikt);
        }
        return weights;
    }
}
