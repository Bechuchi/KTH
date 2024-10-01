package com.bechuchi.model.ViewModel;

import java.util.Locale;

/*
 * skapas för att endast innehålla den data som behövs för visning i gränssnittet.
 */
public class BeehiveViewModel {
    private String MACaddress;
    private double[] weightValues;

    public BeehiveViewModel(String MACaddress, double[] weightValues) {
        this.MACaddress = MACaddress;
        this.weightValues = weightValues;
    }

    public String getMACaddress() {
        return MACaddress;
    }

    public double[] getWeightValues() {
        return weightValues;
    }

    // Lägg till en metod för att returnera vikter som en sträng för visning
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
