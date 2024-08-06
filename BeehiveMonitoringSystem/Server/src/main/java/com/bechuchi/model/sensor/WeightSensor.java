package com.bechuchi.model.sensor;

/***********************
 * Weight Sensor Functions
 ***********************
 * 1. MonitorHoneyProduction()
 * 2. HandlePotentialAbsconding()
 * 3. AlertForHarvestPreparation()
 * 4. ---- Helper Functions ----
 */

public class WeightSensor {
    private double previousWeight;
    private double currentWeight;
    private int dropInWeight;
    private int abscondingThreshold;
    private static int harvestThreshold = 40;

    // Getters and setters

    /***********************
     * - Purpose: Tracks honey weight to estimate production rates.
     * - Method: Regularly check the increase in weight and calculate daily or
     * weekly gains.
     ***********************/
    protected void MonitorHoneyProduction(SensorData sensorData) {
        double currentWeight = sensorData.getWeightFromSensor();
        if (currentWeightIsSignificantlyLessThanPreviousWeight())
            handlePotentialAbsconding();
        if (currentWeight >= harvestThreshold)
            raiseAlert("Plan for harvesting preparation");
    }

    private boolean currentWeightIsSignificantlyLessThanPreviousWeight() {
        return true;
    }

    /***********************
     * - Purpose: Detects sudden drops in weight that might indicate the colony has
     * left the hive.
     * - Method: Compare current weight with historical data and flag drastic
     * reductions.
     ***********************/
    protected void handlePotentialAbsconding() {
        if (dropInWeight > abscondingThreshold)
            raiseAlert("Possible absconding detected");
    }

    /***********************
     * - Purpose: Notifies beekeepers when the honey weight reaches a predefined
     * threshold, suggesting readiness for harvest.
     * - Method: Trigger a notification or update the user interface when the weight
     * threshold is met.
     ***********************/
    protected void alertForHarvestPreparation() {
        raiseAlert("Honey ready for harvest. Current Weight: " + currentWeight);
    }

    /***************
     * HELPER FUNCTIONS
     ***************/
    private void raiseAlert(String alertMessage) {
        System.out.println(alertMessage);
    }
}
