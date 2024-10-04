package com.bechuchi.model;

import java.util.Arrays;

public class ClientMessage {
    private String MACaddress;
    private String IPaddress;
    private int port;
    private int packetID;
    private double[] weightValues;

    public ClientMessage(String MACaddress, String IPaddress, int port, int packetID, double[] weightValues) {
        this.MACaddress = MACaddress;
        this.IPaddress = IPaddress;
        this.port = port;
        this.packetID = packetID;
        this.weightValues = weightValues;
    }

    public String getMACaddress() {
        return MACaddress;
    }

    public int getPort() {
        return port;
    }

    public int getPacketID() {
        return packetID;
    }

    public double[] getWeightValues() {
        return weightValues;
    }

    @Override
    public String toString() {
        return "MAC Address: \t" + MACaddress + '\n' + "IP Address: \t" + IPaddress + '\n' + "PacketID: \t" + packetID
                + '\n' + "WeightValues: \t"
                + Arrays.toString(weightValues);
    }
}