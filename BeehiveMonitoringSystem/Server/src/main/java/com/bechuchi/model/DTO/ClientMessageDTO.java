package com.bechuchi.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * användas för att hantera all information som kommer från klienter och nätverket.
 * DTO (Data Transfer Object) är designat för att hantera data som kommer från klienten via JSON, och därför behöver du inte ha fälten för IP-adress och MAC-adress i DTO
om dessa inte skickas från klienten.
 */
public class ClientMessageDTO {

    @JsonProperty("PacketID")
    private int packetID;

    @JsonProperty("Weight")
    private String weightValues;

    // Getter och setter metoder
    public int getPacketID() {
        return packetID;
    }

    public void setPacketID(int packetID) {
        this.packetID = packetID;
    }

    public String getWeightValues() {
        return weightValues;
    }

    public void setWeightValues(String weightValues) {
        this.weightValues = weightValues;
    }
}
