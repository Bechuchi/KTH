package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bechuchi.model.ClientMessage;
import com.bechuchi.model.MessageProcessor;
import com.bechuchi.model.MessageReceiver;

@Component
public class NetworkController {
    private final MessageReceiver receiver;
    private final MessageProcessor processor;
    final int SERVER_PORT = 9090;

    @Autowired
    public NetworkController(MessageReceiver receiver, MessageProcessor processor) {
        this.receiver = receiver;
        this.processor = processor;
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

                    ClientMessage message = receiver.formatClientMessage(packet);
                    processor.processMessage(message);
                    String ackMessage = "ACK for PacketID";

                    sendResponse(clientAddress, clientPort, ackMessage);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
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
