import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSimulator implements Runnable {
    private final int clientID;

    public ClientSimulator(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            // Skapa en socket för att skicka och ta emot UDP-paket
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.137.1"); // Serverns IP-adress

            // Skapa meddelande att skicka till servern
            String message = MessageCreator.createMessage(clientID);
            byte[] sendData = message.getBytes();

            // Skicka meddelandet till servern
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9090);
            clientSocket.send(sendPacket);
            System.out.println("Client " + clientID + " sent: \t" + message);

            // Skapa en buffer för att ta emot svar från servern
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Vänta på svar från servern
            clientSocket.receive(receivePacket); // Blockerande anrop, väntar på serverns svar
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Client " + clientID + " received response: \t" + response);
            System.out.println();

            // Stäng socket efter att svar har mottagits
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
