package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.bechuchi.model.ClientMessage;
import com.bechuchi.model.MessageProcessor;
import com.bechuchi.model.MessageReceiver;

@Component
public class NetworkController {
    // Lista för att hålla klientmeddelanden
    private final MessageProcessor messageProcessor;
    private volatile String latestJsonData = "";
    final int SERVER_PORT = 9090;

    @Autowired
    public NetworkController(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;

    }

    @PostConstruct
    public void initUdpListener() {
        new Thread(this::listenForIncomingNetworkTraffic).start();
    }

    /*
     * Main Thread: Listens for all incoming messages on port 9090
     * The server creates a socket which is in charge of this process.
     * Socket retrieves incoming message and creates a new thread for a client
     */
    private void listenForIncomingNetworkTraffic() {
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {
            byte[] bufferForIncomingData = new byte[1024];

            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(bufferForIncomingData, bufferForIncomingData.length);
                serverSocket.receive(incomingPacket);

                // Starta en ny tråd för att hantera varje paket
                new Thread(() -> {
                    MessageReceiver receiver = new MessageReceiver();
                    ClientMessage clientMessage = receiver.receiveMessage(incomingPacket);

                    if (clientMessage != null) {
                        this.messageProcessor.processMessage(clientMessage);
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
        }
    }

    /*
     * @GetMapping("/data")
     * public String showData(Model model) {
     * ObjectMapper objectMapper = new ObjectMapper();
     * BeehiveData beehiveData;
     * System.out.println("Latest JSON: " + latestJsonData); // Se vad som finns i
     * try {
     * beehiveData = objectMapper.readValue(latestJsonData, BeehiveData.class);
     * } catch (Exception e) {
     * e.printStackTrace(); // Visa fel om något går fel
     * beehiveData = new BeehiveData(); // Skapa ett tomt objekt i fel fall
     * }
     * 
     * model.addAttribute("beehiveData", beehiveData);
     * 
     * return "dataView";
     * }
     */

    @GetMapping("/test")
    public String showTestData(Model model) {
        return "testView"; // Säkerställ att "dataView.html" har all nödvändig hårdkodad data
    }

    /*
     * private void sendResponse(InetAddress clientAddress, int clientPort, String
     * message) {
     * try {
     * byte[] data = message.getBytes();
     * DatagramPacket sendPacket = new DatagramPacket(data, data.length,
     * clientAddress, clientPort);
     * DatagramSocket socket = new DatagramSocket();
     * socket.send(sendPacket);
     * socket.close();
     * } catch (IOException e) {
     * System.out.println("Error sending response: " + e.getMessage());
     * }
     * }
     */

    /*
     * public void processMessageOld(String receivedData) {
     * try {
     * // String receivedData = new String(incomingPacket.getData(), 0,
     * // incomingPacket.getLength());
     * String latestJsonData;
     * // String receivedData = message.convertByteDataToString(incomingPacket);
     * System.out.println("Received JSON data: " + receivedData);
     * 
     * synchronized (this) {
     * latestJsonData = receivedData;
     * }
     * } catch (Exception e) {
     * System.out.println("Error handling client message: " + e.getMessage());
     * }
     * }
     */

}
