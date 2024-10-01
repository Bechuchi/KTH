package com.bechuchi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.bechuchi.model.ClientMessage;
import com.bechuchi.model.ViewModel.BeehiveViewModel;

import java.util.List;
import java.util.ArrayList;

@Controller
public class ClientDataController {

    // Lista för att hålla klientmeddelanden
    private List<ClientMessage> clientMessages = new ArrayList<>();
    private List<BeehiveViewModel> beehiveDataList = new ArrayList<>();

    // Endpoint för att visa bikupdata
    @GetMapping("/client-data")
    public String showClientData(Model model) {
        // Skicka endast visningsmodeller till gränssnittet
        model.addAttribute("beehiveDataList", beehiveDataList);
        return "clientDataView"; // Namnet på Thymeleaf-vyn
    }

    // Metod för att lägga till nya meddelanden
    public void addClientMessage(ClientMessage clientMessage) {
        // Omvandla ClientMessage till BeehiveViewModel
        BeehiveViewModel viewModel = new BeehiveViewModel(clientMessage.getMACaddress(),
                clientMessage.getWeightValues());
        beehiveDataList.add(viewModel);
    }

    // Endpoint för att visa klientdata
    /*
     * @GetMapping("/client-data")
     * public String showClientData(Model model) {
     * // Lägg till klientmeddelanden i modellen som ska visas på sidan
     * model.addAttribute("clientMessages", clientMessages);
     * return "clientDataView"; // Namnet på Thymeleaf-vyn
     * }
     */

    // Metod för att lägga till nya meddelanden
    /*
     * public void addClientMessage(ClientMessage message) {
     * clientMessages.add(message);
     * }
     */
}
