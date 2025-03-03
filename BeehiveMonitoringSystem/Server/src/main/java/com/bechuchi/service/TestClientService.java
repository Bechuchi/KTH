package com.bechuchi.service;

import org.springframework.stereotype.Service;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

@Service
public class TestClientService {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 9091; // Använd samma port som servern lyssnar på
    private static final Random random = new Random();

    public void sendTestMessage(String macAddress) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // Skapa en testad JSON-sträng (simulerar en riktig bikupa)
            String message = createTestMessage(macAddress);
            byte[] data = message.getBytes();

            // Skicka UDP-paket till servern
            InetAddress serverInetAddress = InetAddress.getByName(SERVER_ADDRESS);
            DatagramPacket packet = new DatagramPacket(data, data.length, serverInetAddress, SERVER_PORT);
            socket.send(packet);

            System.out.println("TestClientService sent: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createTestMessage(String macAddress) {
        // Generera en testad JSON-struktur
        double[] weights = generateWeightValues();
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"ipAddress\": \"127.0.0.1\", ");
        json.append("\"macAddress\": \"").append(macAddress).append("\", ");
        json.append("\"weightValues\": [");

        for (int i = 0; i < weights.length; i++) {
            json.append(weights[i]);
            if (i < weights.length - 1) {
                json.append(", ");
            }
        }

        json.append("]}");
        return json.toString();
    }

    private double[] generateWeightValues() {
        double[] weights = new double[10];
        for (int i = 0; i < 10; i++) {
            weights[i] = 10 + (50 - 10) * random.nextDouble(); // Simulerade viktvärden mellan 10-50 kg
        }
        return weights;
    }
}