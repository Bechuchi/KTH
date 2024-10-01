package com.bechuchi.model;

import java.net.DatagramPacket;

import com.bechuchi.controller.ClientDataController;
import com.bechuchi.model.DTO.ClientMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Denna klass hanterar mottagningen av meddelanden.
 * Den tar emot paket från klienterna och skapar ett objekt som representerar det inkommande meddelandet.
 * Tar emot och konverterar rådata till ett ClientMessage-objekt:
Denna klass ansvarar för att ta emot datan från klienterna och omvandla den till ett objekt av typen ClientMessage.
 */
public class MessageReceiver {

    public ClientMessage receiveMessage(DatagramPacket packet) {

        try {
            // Skapa en ObjectMapper (Jackson JSON parser)
            ObjectMapper objectMapper = new ObjectMapper();

            // Få ut JSON-strängen från paketet
            String messageData = new String(packet.getData(), 0, packet.getLength());

            // Omvandla JSON-strängen till ett ClientMessageDTO-objekt
            ClientMessageDTO dto = objectMapper.readValue(messageData, ClientMessageDTO.class);

            // Hantera viktvärden som en sträng och konvertera till double[]
            double[] weightValues = parseWeights(dto.getWeightValues());

            // Skapa och returnera ett ClientMessage-objekt från DTO:n
            return new ClientMessage(dto.getMACaddress(), dto.getIPaddress(), dto.getPacketId(), weightValues);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private double[] parseWeights(String weightValues) {
        // Dela upp strängen baserat på kommatecken
        String[] weightStrings = weightValues.split(",");
        double[] weights = new double[weightStrings.length];

        // Konvertera varje sträng till ett double-värde
        for (int i = 0; i < weightStrings.length; i++) {
            weights[i] = Double.parseDouble(weightStrings[i].trim());
        }

        return weights;
    }
}
