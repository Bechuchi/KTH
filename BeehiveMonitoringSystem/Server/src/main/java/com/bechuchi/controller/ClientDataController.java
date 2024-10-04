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
    private List<BeehiveViewModel> beehiveDataList = new ArrayList<>();

    // Endpoint för att visa bikupdata
    @GetMapping("/client-data")
    public String showClientData(Model model) {
        // Skicka endast visningsmodeller till gränssnittet
        model.addAttribute("beehiveDataList", beehiveDataList);

        return "clientDataView";
    }

    // Metod för att lägga till nya meddelanden
    public void addClientMessage(ClientMessage beehive) {
        // Omvandla ClientMessage till BeehiveViewModel
        BeehiveViewModel viewModel = new BeehiveViewModel(beehive.getMACaddress(),
                beehive.getWeightValues());
        beehiveDataList.add(viewModel);
    }
}
