package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.bechuchi.model.NetworkHandler;

public class NetworkController {
    private NetworkHandler networkHandler;
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
                processReceivedData(packet);
            } catch (IOException e) {
                System.out.println("Error receiving packet: " + e.getMessage());
                continue;
            }
        }
    }

    private void processReceivedData(DatagramPacket packet) {
        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received data: test " + message);
        // Additional logic to process the message
    }
}