package com.bechuchi.model;

import org.springframework.stereotype.Service;

@Service
public class DataService {
    private String latestData = "";

    public void updateData(String data) {
        this.latestData = data;
    }

    public String getLatestData() {
        return latestData;
    }
}
