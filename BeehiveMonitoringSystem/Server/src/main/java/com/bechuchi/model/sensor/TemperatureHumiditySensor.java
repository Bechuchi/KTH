package com.bechuchi.model.sensor;

/************************************
 * Temperature/Humidity Sensor Functions
 *************************************
 * 1. MonitorHiveConditions()
 * 2. AdjustHiveEnvironment()
 */
public class TemperatureHumiditySensor {
    private double currentTemperature;
    private double currentHumidity;

    enum TemperatureHumidity {
        TEMPERATURE,
        HUMIDITY,
        COMBINATIONVALUE
    }

    public TemperatureHumiditySensor(double inputTemperature, double inputHumidity) {
        this.currentTemperature = inputTemperature;
        this.currentHumidity = inputHumidity;
    }

    // Getters and setters

    /***********************
     * - Purpose: Ensures temperature and humidity levels are within optimal ranges
     * for bee health and productivity.
     * - Method: Regularly record temperature and humidity, comparing them against
     * ideal conditions.
     ***********************/
    protected void monitorHiveConditions(SensorData sensorData) {
        // TemperatureHumidity.COMBINATIONVALUE = getClimateFromSensors();
        currentTemperature = 36.1;
        currentHumidity = 0.55;

        if (!isWithinOptimalRange())
            adjustHiveEnvironment();
    }

    private boolean isWithinOptimalRange() {
        // Analyze combination of currentTemperature and currentHumidity parameters
        return true;
    }

    /***********************
     * - Purpose: Adjusts in-hive conditions when temperature or humidity falls
     * outside healthy ranges.
     * - Method: Activate environmental controls or alert the beekeeper to take
     * manual action.
     ***********************/
    protected void adjustHiveEnvironment() {
        if (currentTemperatureIsTooLowOrTooHigh()) {
            controlHeatingOrCoolingSystems();
        }

        if (currentHumidityIsTooLowOrTooHigh()) {
            controlHumidifiersOrDehumidifiers();
        }
    }

    private boolean currentTemperatureIsTooLowOrTooHigh() {
        return false;
    }

    private void controlHeatingOrCoolingSystems() {

    }

    private boolean currentHumidityIsTooLowOrTooHigh() {
        return false;
    }

    private void controlHumidifiersOrDehumidifiers() {

    }
}