package com.bechuchi.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BeehiveDataStorage {
    private static final String FILE_NAME = "beehive_data.txt";

    // Spara viktdata till fil
    public static void saveWeightData(List<Double> weightValues) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Double weight : weightValues) {
                writer.write(weight.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving weight data: " + e.getMessage());
        }
    }

    // Läs viktdata från fil
    public static List<Double> loadWeightData() {
        List<Double> weightValues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                weightValues.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            System.out.println("⚠️ No previous weight data found, starting fresh.");
        }
        return weightValues;
    }
}
