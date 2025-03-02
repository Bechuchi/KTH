package com.bechuchi.model;

import java.util.ArrayList;
import java.util.List;

public class ClientMessage {

    private String ipAddress;
    private String macAddress;
    private List<Double> weightValues;

    // **Lägg till denna tomma konstruktor**
    public ClientMessage() {
    }

    // **Befintlig konstruktor**
    public ClientMessage(String ipAddress, String macAddress, List<Double> weightValues) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.weightValues = (weightValues != null) ? weightValues : new ArrayList<>(); // Undvik null
    }

    // Getter och Setter metoder
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public List<Double> getWeightValues() {
        return weightValues;
    }

    public void setWeightValues(List<Double> weightValues) {
        this.weightValues = weightValues;
    }
}