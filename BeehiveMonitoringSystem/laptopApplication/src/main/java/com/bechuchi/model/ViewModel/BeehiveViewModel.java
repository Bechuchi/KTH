package com.bechuchi.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

/*
 * skapas för att endast innehålla den data som behövs för visning i gränssnittet.
 */
public class BeehiveViewModel {
    private String ipAddress;
    private String macAddress;
    private List<Double> weightValues;

    public BeehiveViewModel(String ipAddress, String macAddress, List<Double> weightValues) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.weightValues = (weightValues != null) ? new ArrayList<>(weightValues) : new ArrayList<>();
        System.out.println("Skapar BeehiveViewModel, weightValues: " + this.weightValues);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public List<Double> getWeightValues() {
        return weightValues;
    }

    @Override
    public String toString() {
        return "BeehiveViewModel{" +
                "macAddress='" + macAddress + '\'' +
                ", weightValues=" + weightValues +
                '}';
    }
}
