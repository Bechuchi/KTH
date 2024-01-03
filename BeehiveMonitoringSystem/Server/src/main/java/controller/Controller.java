package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * Hanterar inkommande meddelanden och koordinera behandlingen av dem.
 */
public class Controller {
    final int port = 8080;
    final InetAddress IPAddress;

    public Controller() throws UnknownHostException {
        try {
            IPAddress = InetAddress.getByName("192.168.1.97");
        } catch (UnknownHostException e) {
            throw e;
        }
    }

    /*
     * Periodiskt kontrollerar om det har kommit inkommande data från
     * Arduino-klienterna.
     * Detta kan göras med en enkel loop som tittar på en specifik port och väntar
     * på UDP-paket.
     * Om det finns inkommande data, kan du hantera den enligt dina krav.
     */
    public void scanLocalArea() throws IOException {

        // Step 1: Create a socket to listen at port 1234
        DatagramSocket socket = new DatagramSocket(port, IPAddress);
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