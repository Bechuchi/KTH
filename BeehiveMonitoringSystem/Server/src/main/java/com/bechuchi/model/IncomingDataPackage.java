package com.bechuchi.model;

import java.util.List;

/*
 * I IncomingDataPackage-klassen använder vi en lista av SensorData för att hantera olika typer av sensordata.
 * Detta ger flexibilitet att inkludera olika sensorer i framtiden. printInfo-metoden ger ett exempel på hur
 * du kan skriva ut informationen från ett sådant meddelande, men du kan utöka detta med mer detaljerad
 * logik för att hantera och visa sensordata.
    
 * Denna struktur ger en bra grund för att hantera inkommande data på ett strukturerat sätt,
 * och du kan anpassa och utöka klasserna ytterligare för att passa dina specifika behov.
 */
public class IncomingDataPackage extends Message {
    private Client client;
    private List<SensorData> sensorDataList;

    public IncomingDataPackage(String ID, long timestamp, Client client, List<SensorData> sensorDataList) {
        super(ID, timestamp);
        this.client = client;
        this.sensorDataList = sensorDataList;
    }

    @Override
    public void printInfo() {
        System.out.println("ID: " + getID());
        System.out.println("Timestamp: " + getTimestamp());
        System.out.println("Client IP: " + client.getIPAddress());
        // Iterera över sensorDataList för att skriva ut sensorinformation
    }

    // Getters och eventuella setters
}
