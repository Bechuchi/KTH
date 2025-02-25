package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bechuchi.model.ClientMessage;
import com.bechuchi.service.BeehiveDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This controller is responsioble for managing the network traffic.
 * It listens for incoming data, sends a reply to the recipient and
 * forwards the data to an internal component in the Laptop Application
 * responsible for processing the information.
 * 
 * The recievement of data is done with UDP.
 */
@Component
public class NetworkController {
    private final BeehiveDataService dataService;
    final int SERVER_PORT = 9091;

    @Autowired
    public NetworkController(BeehiveDataService dataService) {
        this.dataService = dataService;
    }

    @PostConstruct
    public void initUDPListener() {
        new Thread(this::processIncomingData).start();
    }

    private void processIncomingData() {
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet);

                new Thread(() -> {
                    InetAddress clientAddress = packet.getAddress();
                    int clientPort = packet.getPort();

                    // Konvertera inkommande UDP-paket till en ClientMessage
                    ClientMessage message = convertPacketToClientMessage(packet);
                    if (message != null) {
                        dataService.addClientMessage(message);
                    }

                    // dataService.addClientMessage(message);

                    String ackMessage = "ACK for PacketID";

                    sendResponse(clientAddress, clientPort, ackMessage);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
        }
    }

    private ClientMessage convertPacketToClientMessage(DatagramPacket packet) {
        try {
            String messageData = new String(packet.getData(), 0, packet.getLength());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(messageData, ClientMessage.class);
        } catch (Exception e) {
            System.out.println("❌ Fel vid konvertering av meddelande: " + e.getMessage());
            return null;
        }
    }

    private void sendResponse(InetAddress clientAddress, int clientPort, String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length,
                    clientAddress, clientPort);
            DatagramSocket socket = new DatagramSocket();
            socket.send(sendPacket);
            socket.close();
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}