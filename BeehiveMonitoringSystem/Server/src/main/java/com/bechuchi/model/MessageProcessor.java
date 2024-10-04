package com.bechuchi.model;

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

    // Bearbetar och loggar den mottagna informationen
    public void processMessage(ClientMessage message) {
        printMessage(message);
        dataController.addClientMessage(message);
    }

    private void printMessage(ClientMessage message) {
        System.out.println(message);
        System.out.println();
    }
}
