package com.bechuchi.model;

public class ClientMessage {

    private String ipAddress;
    private String macAddress;
    private double[] weightValues;

    // **Lägg till denna tomma konstruktor**
    public ClientMessage() {
    }

    // **Befintlig konstruktor**
    public ClientMessage(String ipAddress, String macAddress, double[] weightValues) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.weightValues = weightValues;
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

    public double[] getWeightValues() {
        return weightValues;
    }

    public void setWeightValues(double[] weightValues) {
        this.weightValues = weightValues;
    }
}