package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bechuchi.model.PacketHandler;

@Component
public class NetworkController {
    private final PacketHandler packetHandler;
    final int RECIEVE_PORT = 8080;
    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;

    @Autowired
    public NetworkController(PacketHandler packetHandler) {
        try {
            recieveSocket = new DatagramSocket(RECIEVE_PORT);
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Error creating DatagramSocket: " + e.getMessage());
        }
        this.packetHandler = packetHandler;
    }

    @PostConstruct
    public void createListeningThread() {
        new Thread(this::processIncomingMessages).start();
    }

    private void processIncomingMessages() {
        try {
            byte[] temporaryBuffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(temporaryBuffer, temporaryBuffer.length);
                recieveSocket.receive(packet); // är en blockerande metod. HÄR hämtas nästa paket från OS-bufferten
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                new Thread(() -> {
                    int packetID = packetHandler.handleIncomingUdpPacket(packet, clientAddress, clientPort);
                    String ack = "ACK for PacketID:\t" + packetID;
                    sendResponse(clientAddress, clientPort, ack);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
        }
    }

    private void sendResponse(InetAddress ipAddress, int clientPort, String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, ipAddress, 9090);
            sendSocket.send(sendPacket);
            System.out.println("ACK sent: " + message);
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}