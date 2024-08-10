package com.bechuchi.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/*
* Responsible for all direct communication over the network. This class manages the recievement
* of data packages and the forwaring of this information to the ClientController, which in turn continues
* the processing of the incoming data.
*/
public class NetworkHandler {
    private int port;
    private DatagramSocket socket;

    public NetworkHandler(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(this.port);
    }

    public DatagramPacket receivePacket() throws IOException {
        byte[] buffer = new byte[65535];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        return packet;
    }

    public String jsonFormatter(DatagramPacket packet) {
        String jsonData = new String(packet.getData(), 0, packet.getLength());

        return jsonData;
    }
}
