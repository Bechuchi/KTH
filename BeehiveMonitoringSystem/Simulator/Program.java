import com.fasterxml.jackson.databind.ObjectMapper;

public class Program {
    public static void main(String[] args) {
        try {
            // Skapa flera simulerade klienter
            for (int i = 1; i <= 5; i++) {
                int clientID = i;

                // Starta en ny tråd för varje klient
                new Thread(new ClientSimulator(clientID)).start();
                Thread.sleep(1000); // Liten fördröjning mellan varje klient
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}