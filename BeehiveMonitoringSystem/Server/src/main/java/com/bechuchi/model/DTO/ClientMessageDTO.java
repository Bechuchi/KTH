package com.bechuchi.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * användas för att hantera all information som kommer från klienter och nätverket.
 */
public class ClientMessageDTO {

    @JsonProperty("MACaddress")
    private String MACaddress;

    @JsonProperty("IPaddress")
    private String IPaddress;

    @JsonProperty("PacketID")
    private int packetId;

    @JsonProperty("Weight")
    private String weightValues;

    // Getter och setter metoder
    public String getMACaddress() {
        return MACaddress;
    }

    public void setMACAddress(String MACaddress) {
        this.MACaddress = MACaddress;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public String getWeightValues() {
        return weightValues;
    }

    public void setWeightValues(String weightValues) {
        this.weightValues = weightValues;
    }
}
