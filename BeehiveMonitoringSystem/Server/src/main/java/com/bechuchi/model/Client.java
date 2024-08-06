package com.bechuchi.model;

import com.bechuchi.model.sensor.SensorData;

public class Client {
    private String IPAddress;
    private int ID;
    private SensorData sensorData;

    public Client(String IPAddress, SensorData sensorData) {
        this.IPAddress = IPAddress;
        this.ID = Integer.parseInt(IPAddress);
        this.sensorData = sensorData;
    }
}
