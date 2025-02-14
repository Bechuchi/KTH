package com.bechuchi.model;

public class Client {
    private String IPAddress;

    public Client(String inputIPAddress) {
        this.IPAddress = inputIPAddress;
    }

    // Getters och setters
    public String getIPAddress() {
        return IPAddress;
    }
}