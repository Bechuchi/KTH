package com.bechuchi.model;

import java.util.List;

public class SensorData {
    private List<Double> weightData;
    private List<TemperatureHumiditySensor> tempHumData;

    public SensorData(List<Double> weightData, List<TemperatureHumiditySensor> tempHumData) {
        this.weightData = weightData;
        this.tempHumData = tempHumData;
    }

    // Getters and setters
    protected double getWeightFromSensor() {
        double weight = 10.0;
        return weight;
    }
}