package com.bechuchi.model.ViewModel;

import java.util.Locale;

/*
 * skapas för att endast innehålla den data som behövs för visning i gränssnittet.
 */
public class BeehiveViewModel {
    private String ipAddress;
    private String macAddress;
    private double[] weightValues;

    public BeehiveViewModel(String ipAddress, String macAddress, double[] weightValues) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.weightValues = weightValues;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double[] getWeightValues() {
        return weightValues;
    }

    public String getWeightValuesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < weightValues.length; i++) {
            sb.append(String.format(Locale.US, "%.2f", weightValues[i]));
            if (i < weightValues.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
