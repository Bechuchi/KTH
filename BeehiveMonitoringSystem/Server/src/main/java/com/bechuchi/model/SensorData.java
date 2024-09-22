package com.bechuchi.model;

public abstract class SensorData {
    public abstract void processData();

    public abstract void generateAlerts();

    public abstract void performThresholdAnalysis();

}