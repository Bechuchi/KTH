package com.bechuchi.model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bechuchi.controller.ClientDataController;

/*
 * En klass som ansvarar för basal bearbetning av varje mottaget meddelande,
 * såsom att extrahera och kanske logga viktvärden, IP-adresser osv.
 */
@Component
public class MessageProcessor {
    // Injicera ClientDataController
    private final ClientDataController dataController;

    @Autowired
    public MessageProcessor(ClientDataController dataController) {
        this.dataController = dataController;

    }

    private void printMessage(ClientMessage message) {
        System.out.println(message);
        System.out.println();
    }

    // Bearbetar och loggar den mottagna informationen
    public void processMessage(ClientMessage message) {
        System.out.println("--------------------------------");
        System.out.println("Processing Message from Client:");
        System.out.println("--------------------------------");
        printMessage(message);
        // Lägg till mer logik för att bearbeta viktvärden, skicka svar till klient,
        // etc.
        // Lägg till meddelandet i controllern
        dataController.addClientMessage(message);
    }
}
