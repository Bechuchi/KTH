import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SimulatorProgram {
    public static void main(String[] args) {
        try {
            // Skapa flera simulerade klienter
            for (int i = 1; i <= 5; i++) {
                // Starta en ny tråd för varje klient
                int clientId = i;
                new Thread(() -> simulateClient(clientId)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void simulateClient(int clientId) {
        try {
            // Skicka ett meddelande till servern
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.137.1"); // Serverns IP-adress
            String message = "Dummy data from client is now" + clientId; // Dummy-data
            byte[] buffer = message.getBytes();

            // Paketet skickas till servern på port 9090
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, 9090);
            socket.send(packet);
            System.out.println("Client " + clientId + " sent data to server.");

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
