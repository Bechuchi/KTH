package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bechuchi.model.ClientMessage;
import com.bechuchi.service.BeehiveDataService;
import com.bechuchi.service.BeehiveDataStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * The NetworkController is responsible for handling network traffic.
 * It listens for incoming UDP messages from clients (beehive monitoring devices)
 * and processes the received data. Once a message is received:
 */
@Component
public class NetworkController {
    private final BeehiveDataService dataService;
    private final BeehiveDataStorage dataStorage;
    // final int SERVER_PORT = 9091;
    final int RECIEVE_PORT = 8080;
    final int SEND_PORT = 9090;

    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;

    @Autowired
    public NetworkController(BeehiveDataService dataService, BeehiveDataStorage dataStorage) {
        try {
            recieveSocket = new DatagramSocket(RECIEVE_PORT); // Skapa en socket en gång
            sendSocket = new DatagramSocket(SEND_PORT); // Skapa en socket en gång
        } catch (SocketException e) {
            System.out.println("Error creating DatagramSocket: " + e.getMessage());
        }
        this.dataService = dataService;
        this.dataStorage = dataStorage;
    }

    @PostConstruct
    public void initUDPListener() {
        new Thread(this::processIncomingData).start();
    }

    private void processIncomingData() {
        try {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                recieveSocket.receive(packet);

                new Thread(() -> {
                    InetAddress clientAddress = packet.getAddress();
                    int clientPort = packet.getPort();

                    ClientMessage currentBeehive = convertPacketToClientMessage(packet);

                    if (currentBeehive != null) {
                        dataService.processIncomingMessage(currentBeehive);
                        dataService.storeWeightData(currentBeehive.getMacAddress(), currentBeehive.getWeightValues());
                        String ackMessage = "ACK for PacketID";
                        sendResponse(clientAddress, clientPort, ackMessage);
                    }
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

    private void sendResponse(InetAddress clientIPAddress, int clientPort, String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, clientIPAddress, 9090); // Skicka på 9090
            sendSocket.send(sendPacket);
            System.out.println("ACK sent: " + message);
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }

    private void sendResponseOld(InetAddress clientIPAddress, int clientPort, String message) {
        try {
            System.out.println("Send Response");
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length,
                    clientIPAddress, clientPort);
            sendSocket.send(sendPacket);
            // recieveSocket.close();
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}