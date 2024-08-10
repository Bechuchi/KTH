package com.bechuchi.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bechuchi.model.DataService;
import com.bechuchi.model.IncomingDataPacket;
import com.bechuchi.model.NetworkHandler;

@Controller
public class NetworkController {
    private NetworkHandler networkHandler;
    private String latestJsonData = "";
    private IncomingDataPacket incomingDataPacket;
    final int INCOMING_PORT = 9090;
    final InetAddress SERVER_IP_ADDRESS;
    final String CLIENT_IP_ADDRESS = "192.168.137.134";

    public NetworkController() throws UnknownHostException, SocketException {
        try {
            SERVER_IP_ADDRESS = InetAddress.getByName("192.168.1.97");
            // this.networkHandler = new NetworkHandler(INCOMING_PORT);
        } catch (UnknownHostException e) {
            throw e;
        }
    }

    @PostConstruct
    public void initUdpListener() {
        new Thread(this::listenForIncomingNetworkTraffic).start();
    }

    private void listenForIncomingNetworkTraffic() {
        try (DatagramSocket socket = new DatagramSocket(INCOMING_PORT)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String receivedData = new String(packet.getData(), 0, packet.getLength());
                printReceivedData(packet);
                synchronized (this) {
                    latestJsonData = receivedData;
                }
            }
        } catch (IOException e) {
            System.out.println("Error receiving UDP packet: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public String showData(Model model) {
        model.addAttribute("data", latestJsonData); // Passa den senaste mottagna datan till vyn
        return "dataView"; // Thymeleaf-vyn som ska rendera datan
    }

    public void printReceivedData(DatagramPacket packet) {
        String timeStamp = setTimeStamp();
        String incomingDataPacket = new String(packet.getData(), 0, packet.getLength());

        System.out.println("######################");
        System.out.println("Client Messagee: " + incomingDataPacket + " " + timeStamp);
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