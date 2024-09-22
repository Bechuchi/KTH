package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bechuchi.model.BeehiveData;
import com.bechuchi.model.HelperFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClientController {
    private HelperFunctions help = new HelperFunctions();
    private volatile String latestJsonData = "";
    final int SERVER_PORT = 9090; // Lyssnar på samma port för alla klienter

    @PostConstruct
    public void initUdpListener() {
        new Thread(this::listenForIncomingNetworkTraffic).start();
    }

    private void listenForIncomingNetworkTraffic() {
        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                // Skapa en tråd för att hantera klientens meddelande
                new Thread(() -> handleClientMessage(packet)).start();
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
        }
    }

    private void handleClientMessage(DatagramPacket packet) {
        try {
            // Läs meddelandet från klienten
            String receivedData = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client Controller");
            help.printReceivedData(packet);

            // Hämta klientens IP-adress och port
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            // Skicka svar tillbaka till klienten
            sendResponse(clientAddress, clientPort, "Message Acknowledged");

            // Uppdatera den senaste mottagna datan
            synchronized (this) {
                latestJsonData = receivedData;
            }
        } catch (Exception e) {
            System.out.println("Error handling client message: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public String showData(Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        BeehiveData beehiveData;
        try {
            beehiveData = objectMapper.readValue(latestJsonData, BeehiveData.class);
        } catch (Exception e) {
            e.printStackTrace(); // Visa fel om något går fel
            beehiveData = new BeehiveData(); // Skapa ett tomt objekt i fel fall
        }

        model.addAttribute("beehiveData", beehiveData);

        return "dataView";
    }

    @GetMapping("/test")
    public String showTestData(Model model) {
        return "testView"; // Säkerställ att "dataView.html" har all nödvändig hårdkodad data
    }

    private void sendResponse(InetAddress clientAddress, int clientPort, String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
            DatagramSocket socket = new DatagramSocket();
            socket.send(sendPacket);
            socket.close();
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}
