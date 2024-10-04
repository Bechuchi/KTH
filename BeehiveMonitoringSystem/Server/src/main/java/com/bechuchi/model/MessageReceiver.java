package com.bechuchi.model;

import java.util.Random;
import java.net.DatagramPacket;
import java.net.InetAddress;

import org.springframework.stereotype.Component;
import com.bechuchi.model.DTO.ClientMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Denna klass hanterar mottagningen av meddelanden.
 * Den tar emot paket från klienterna och skapar ett objekt som representerar det inkommande meddelandet.
 * Tar emot och konverterar rådata till ett ClientMessage-objekt:
Denna klass ansvarar för att ta emot datan från klienterna och omvandla den till ett objekt av typen ClientMessage.
 */
@Component
public class MessageReceiver {

    public ClientMessage formatClientMessage(DatagramPacket inputPacket) {

        try {
            // Skapa en ObjectMapper (Jackson JSON parser)
            ObjectMapper objectMapper = new ObjectMapper();

            // Få ut JSON-strängen från paketet
            String messageData = new String(inputPacket.getData(), 0, inputPacket.getLength());

            // Omvandla JSON-strängen till ett ClientMessageDTO-objekt
            ClientMessageDTO dto = objectMapper.readValue(messageData, ClientMessageDTO.class);

            double[] weightValues = parseWeights(dto.getWeightValues());

            String MACaddress = "00-B0-D0-63-C2-26";
            InetAddress IPaddress = inputPacket.getAddress();
            int port = inputPacket.getPort();

            // Skapa och returnera ett ClientMessage-objekt från DTO:n
            return new ClientMessage(MACaddress, IPaddress.getHostAddress(), port, dto.getPacketID(),
                    weightValues);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private double[] parseWeights(String weightValues) {
        String[] weightStrings = weightValues.split(",");
        double[] weights = new double[weightStrings.length];

        // Konvertera varje sträng till ett double-värde
        for (int i = 0; i < weightStrings.length; i++) {
            weights[i] = Double.parseDouble(weightStrings[i].trim());
        }

        return weights;
    }

    public void logMessage() {

    }
}
