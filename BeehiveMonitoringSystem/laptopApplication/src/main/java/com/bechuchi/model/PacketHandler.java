package com.bechuchi.model;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PacketHandler {
    private final Map<String, List<BeehiveViewModel>> beehiveDataMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public PacketHandler() {

    }

    public int handleIncomingUdpPacket(DatagramPacket packet, InetAddress address, int port) {
        Beehive currentBeehive = parseUdpPacket(packet);

        if (currentBeehive == null)
            return 0;

        processBeehiveData(currentBeehive);
        currentBeehive.getWeightValues();

        int packetID = currentBeehive.getPacketID();
        return packetID;
    }

    public void processBeehiveData(Beehive currentBeehive) {
        String macAddress = currentBeehive.getMacAddress();
        List<Double> newWeights = currentBeehive.getWeightValues();

        // Hämta bikupelistan för den specifika MAC-adressen
        List<BeehiveViewModel> beehiveList = beehiveDataMap.computeIfAbsent(macAddress, k -> new ArrayList<>());

        // Om bikupan redan finns, uppdatera dess viktvärden
        if (!beehiveList.isEmpty()) {
            BeehiveViewModel existingBeehive = beehiveList.get(0);
            existingBeehive.getWeightValues().addAll(newWeights);
        } else {
            // Skapa en ny bikupa endast om ingen redan finns
            beehiveList.add(new BeehiveViewModel(
                    currentBeehive.getIpAddress(),
                    macAddress,
                    newWeights));
        }
    }

    private Beehive parseUdpPacket(DatagramPacket currentPacket) {
        try {
            String json = new String(currentPacket.getData(), 0, currentPacket.getLength());
            return objectMapper.readValue(json, Beehive.class);
        } catch (Exception e) {
            System.out.println("❌ Failed to parse UDP message: " + e.getMessage());
            return null;
        }
    }

    public Map<String, List<BeehiveViewModel>> getAllBeehiveData() {

        return beehiveDataMap;
    }
}