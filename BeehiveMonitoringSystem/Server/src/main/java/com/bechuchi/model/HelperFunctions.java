package com.bechuchi.model;

import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelperFunctions {
    public void printReceivedData(DatagramPacket packet) {
        String timeStamp = setTimeStamp();
        String incomingDataPacket = new String(packet.getData(), 0, packet.getLength());

        System.out.println("######################");
        System.out.println("Client Message is: " + incomingDataPacket + " " + timeStamp);
        System.out.println("######################");
    }

    private String setTimeStamp() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timeStamp = myDateObj.format(myFormatObj);

        return timeStamp;
    }

    private SensorData parseSensorData(String jsonData) {
        // Implementering för att tolka JSON till specifika SensorData-implementeringar
        // Använd Jackson eller Gson för att avserialisera JSON till lämplig klass
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Exempel för att avgöra typen:
            if (jsonData.contains("Weight")) {
                return mapper.readValue(jsonData, WeightData.class);
            } else if (jsonData.contains("Temperature")) {
                return mapper.readValue(jsonData, ClimateData.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
