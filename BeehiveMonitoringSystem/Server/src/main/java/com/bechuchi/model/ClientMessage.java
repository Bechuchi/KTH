package com.bechuchi.model;

import java.util.Arrays;
import java.util.Locale;

/*
 * En modellklass som representerar varje meddelande från en klient.
 * Den lagrar information som paket-ID, IP-adress och viktvärden.
 */
public class ClientMessage {
    private String MACaddress;
    private String IPaddress;
    private int packetId;
    private double[] weightValues;

    public ClientMessage(String MACaddress, String IPaddress, int packetId, double[] weightValues) {
        this.MACaddress = MACaddress;
        this.IPaddress = IPaddress;
        this.packetId = packetId;
        this.weightValues = weightValues;
    }

    // Getter-metoder för att hämta information
    public String getMACaddress() {
        return MACaddress;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public int getPacketId() {
        return packetId;
    }

    public double[] getWeightValues() {
        return weightValues;
    }

    // Konvertera double[] till en läsbar sträng
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

    @Override
    public String toString() {
        return "MAC Address: \t" + MACaddress + '\n' + "IP Address: \t" + IPaddress + '\n' + "PacketID: \t" + packetId
                + '\n' + "WeightValues: \t"
                + Arrays.toString(weightValues);
    }
}