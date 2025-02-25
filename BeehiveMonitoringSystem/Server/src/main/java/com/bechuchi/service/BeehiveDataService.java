package com.bechuchi.service;

import org.springframework.stereotype.Service;

import com.bechuchi.model.ClientMessage;
import com.bechuchi.model.ViewModel.BeehiveViewModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeehiveDataService {
    private final List<BeehiveViewModel> beehiveData = new ArrayList<>();

    public void addClientMessage(ClientMessage beehive) {
        System.out.println("✅ Lägger till data: " + beehive);

        BeehiveViewModel viewModel = new BeehiveViewModel(
                beehive.getIpAddress(),
                beehive.getMacAddress(),
                beehive.getWeightValues());

        beehiveData.add(viewModel);
        System.out.println("📊 Aktuell lista: " + beehiveData);
    }

    public List<BeehiveViewModel> getAllBeehiveData() {
        return beehiveData;
    }

    public void processIncomingMessage(ClientMessage message) {
        System.out.println("🔍 Processing message in Service Layer: " + message);

        BeehiveViewModel viewModel = new BeehiveViewModel(
                message.getIpAddress(),
                message.getMacAddress(),
                message.getWeightValues());

        beehiveData.add(viewModel);
    }

}
