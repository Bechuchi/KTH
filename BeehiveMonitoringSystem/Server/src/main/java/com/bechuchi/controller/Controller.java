package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    final int PORT = 9090;
    final InetAddress SERVER_IP_ADDRESS;
    final String CLIENT_IP_ADDRESS = "192.168.137.134";

    public Controller() throws UnknownHostException {
        try {
            SERVER_IP_ADDRESS = InetAddress.getByName("192.168.1.97");
        } catch (UnknownHostException e) {
            throw e;
        }
    }

    public void scanLocalArea() throws IOException {
        // Step 1: Create a socket to listen at port
        // DatagramSocket socket = new DatagramSocket(PORT, SERVER_IP_ADDRESS);
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] dataBufferForReceivingData = new byte[65535];

        DatagramPacket incomingPacket = null;

        while (true) {
            // Step 2: create a DatgramPacket to receive the data.
            incomingPacket = new DatagramPacket(dataBufferForReceivingData, dataBufferForReceivingData.length);

            // Step 3: revieve the data in byte buffer.
            socket.receive(incomingPacket);
            String clientMessage = getClientMessage(dataBufferForReceivingData).toString();
            System.out.print("Client says: " + clientMessage + "\n");
            // Clear the buffer after every message.
            dataBufferForReceivingData = new byte[65535];
        }
    }

    private static StringBuilder getClientMessage(byte[] incomingBytes) {
        if (incomingBytes == null) {
            return null;
        }

        StringBuilder clientMessage = new StringBuilder();
        int i = 0;

        while (incomingBytes[i] != 0) {
            clientMessage.append((char) incomingBytes[i]);
            i++;
        }

        return clientMessage;
    }
}