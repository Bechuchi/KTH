package com.bechuchi.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BeehiveDataStorage {
    private static final String FILE_NAME = "beehive_data.txt";

    public static void saveWeightData(String macAddress, List<Double> weightValues) {
        System.out.println("**************************************");
        System.out.println("DataStorage: saveWeightData()");
        System.out.println("Vikt som ska sparas i textfilen: " + weightValues);
        System.out.println("**************************************");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("MAC:" + macAddress);
            writer.newLine();
            for (Double weight : weightValues) {
                writer.write(weight.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving weight data: " + e.getMessage());
        }
    }

    public static Map<String, List<Double>> loadWeightData() {
        Map<String, List<Double>> weightData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":"); // Om du sparar i formatet "MAC:20.5,30.2..."
                if (parts.length == 2) {
                    String mac = parts[0];
                    List<Double> weights = Arrays.stream(parts[1].split(","))
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());

                    weightData.put(mac, weights);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ No previous weight data found, starting fresh.");
        }

        return weightData; // Returnerar en tom map istället för null
    }
}
