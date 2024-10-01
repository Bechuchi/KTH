/*
 * Ansvarar för att simulera varje klient.
Denna klass representerar varje individuell klient och hanterar kommunikation via UDP.
Den använder MessageCreator för att generera meddelanden och skickar dessa till servern.
Simulerar en klient som skickar meddelanden till servern
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSimulator implements Runnable {
    private int clientID;

    public ClientSimulator(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.137.1"); // Serverns IP-adress

            // Skapa och skicka meddelande
            String message = MessageCreator.createMessage(clientID);
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, 9090);
            socket.send(packet);

            System.out.println("Client " + clientID + " sent: " + message + '\n');

            Thread.sleep(500); // Liten fördröjning mellan meddelanden
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
