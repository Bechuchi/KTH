package com.bechuchi.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeehiveData {
    @JsonProperty("IPaddress")
    private String ipAddress;

    @JsonProperty("Weight")
    private List<Integer> weight;

    // Getters
    public String getIpAddress() {
        return ipAddress;
    }

    public List<Integer> getWeight() {
        return weight;
    }

    // Setters
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    // Anpassad setter som konverterar en kommaseparerad sträng till en lista av
    // heltal
    @JsonProperty("Weight")
    public void setWeight(String weight) {
        this.weight = Arrays.stream(weight.split(","))
                .map(String::trim) // Ta bort eventuella mellanslag
                .map(Integer::parseInt) // Konvertera varje sträng till ett heltal
                .collect(Collectors.toList());
    }
}
