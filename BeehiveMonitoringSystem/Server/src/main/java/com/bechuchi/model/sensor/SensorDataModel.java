package com.bechuchi.model.sensor;

public class SensorDataModel {
    private double temperature;
    private double humidity;

    // Konstruktorer, getters och setters
    public SensorDataModel(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
