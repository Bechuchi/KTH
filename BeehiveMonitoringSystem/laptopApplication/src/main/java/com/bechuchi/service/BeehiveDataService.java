package com.bechuchi.service;

import org.springframework.stereotype.Service;
import com.bechuchi.model.ClientMessage;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BeehiveDataService {
    private final Map<String, List<BeehiveViewModel>> beehiveDataMap = new HashMap<>();

    public BeehiveDataService() {
    }

    public void loadStoredWeightData() {
        Map<String, List<Double>> loadedWeights = BeehiveDataStorage.loadWeightData();

        if (loadedWeights != null && !loadedWeights.isEmpty()) {
            for (Map.Entry<String, List<Double>> entry : loadedWeights.entrySet()) {
                String mac = entry.getKey();
                List<Double> weights = entry.getValue();

                if (weights != null && !weights.isEmpty()) {
                    beehiveDataMap.computeIfAbsent(mac, k -> new ArrayList<>())
                            .add(new BeehiveViewModel("unknown-ip", mac, weights));
                }
            }
        }
    }

    public void processIncomingMessage(ClientMessage beehive) {
        System.out.println("**************************************");
        System.out.println("DataService: ProcessIncomingMessage()");
        System.out.println("Mottagen beehive-data: " + beehive);
        System.out.println("**************************************");

        String macAddress = beehive.getMacAddress();
        List<Double> newWeights = beehive.getWeightValues();

        // Hämta bikupelistan för den specifika MAC-adressen
        List<BeehiveViewModel> beehiveList = beehiveDataMap.computeIfAbsent(macAddress, k -> new ArrayList<>());

        // Om bikupan redan finns, uppdatera dess viktvärden
        if (!beehiveList.isEmpty()) {
            BeehiveViewModel existingBeehive = beehiveList.get(0);
            existingBeehive.getWeightValues().addAll(newWeights);
        } else {
            // Skapa en ny bikupa endast om ingen redan finns
            beehiveList.add(new BeehiveViewModel(
                    beehive.getIpAddress(),
                    macAddress,
                    newWeights));
        }

        System.out.println("Efter tillägg, beehiveDataMap: " + beehiveDataMap);
        System.out.println("**************************************");

        // Spara vikten men utan att skapa ny bikupa
        // storeWeightData(macAddress, newWeights);
    }

    public void storeWeightData(String macAddress, List<Double> newWeights) {
        System.out.println("**************************************");
        System.out.println("DataService: storeWeightData()");
        System.out.println("Mottagen vikt-data: " + newWeights);
        System.out.println("**************************************");
        List<BeehiveViewModel> beehiveList = beehiveDataMap.get(macAddress);

        if (beehiveList != null && !beehiveList.isEmpty()) {
            // Uppdatera den existerande bikupan istället för att skapa en ny
            BeehiveViewModel beehive = beehiveList.get(0);
            // beehive.getWeightValues().addAll(newWeights);
            System.out.println("Inter viktlista: " + beehive.getWeightValues());
        } else {
            System.out.println("⚠️ Ingen bikupa hittades för MAC: " + macAddress);
        }

        System.out.println("**************************************");
        // Spara till fil
        BeehiveDataStorage.saveWeightData(macAddress, newWeights);
    }

    public Map<String, List<BeehiveViewModel>> getAllBeehiveData() {
        System.out.println("**************************************");
        System.out.println("DataService: getAllBeehiveData()");
        System.out.println("Innehållet som lagras i beehiveDataMap: " + beehiveDataMap);
        System.out.println("**************************************");

        /*
         * List<BeehiveViewModel> beehiveList = beehiveDataMap.get("1B:B2:16:8A:37:Y");
         * BeehiveViewModel beehive = beehiveList.get(0);
         * return beehive.getWeightValues();
         */

        return beehiveDataMap;
    }
}
