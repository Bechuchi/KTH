package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bechuchi.model.IncomingDataPacket;
import com.bechuchi.model.NetworkHandler;

public class NetworkController {
    private NetworkHandler networkHandler;
    private IncomingDataPacket incomingDataPacket;
    final int PORT = 9090;
    final InetAddress SERVER_IP_ADDRESS;
    final String CLIENT_IP_ADDRESS = "192.168.137.134";

    public NetworkController() throws UnknownHostException, SocketException {
        try {
            SERVER_IP_ADDRESS = InetAddress.getByName("192.168.1.97");
            this.networkHandler = new NetworkHandler(PORT);
        } catch (UnknownHostException e) {
            throw e;
        }
    }

    public void listenForIncomingNetworkTraffic() {
        while (true) {
            try {
                DatagramPacket packet = networkHandler.receivePacket();
                printReceivedData(packet);
                sendResponse(packet.getAddress(), packet.getPort(), "Message Recieved");
            } catch (IOException e) {
                System.out.println("Error receiving packet: " + e.getMessage());
                continue;
            }
        }
    }

    public void printReceivedData(DatagramPacket packet) {
        String timeStamp = setTimeStamp();
        String incomingDataPacket = new String(packet.getData(), 0, packet.getLength());

        System.out.println("######################");
        System.out.println("Client Message: " + incomingDataPacket + " " + timeStamp);
        System.out.println("######################");
    }

    private String setTimeStamp() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeStamp = myDateObj.format(myFormatObj);

        return timeStamp;
    }

    public void sendResponse(InetAddress clientAddress, int clientPort, String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
            DatagramSocket socket = new DatagramSocket();
            socket.send(sendPacket);
            socket.close();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
}