package com.bechuchi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import com.bechuchi.service.BeehiveDataService;

import java.util.List;

/*
 * The PresentationController is responsible for handling user interactions and
 * managing how data is presented in the UI. It retrieves processed data from
 * BeehiveDataService and sends it to the View.
 */
@Controller
public class PresentationController {
    private final BeehiveDataService dataService;

    @Autowired
    public PresentationController(BeehiveDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/console")
    @ResponseBody
    public String printToConsole() {
        System.out.println("Hello Again from Spring Boot!");
        return "Check Again console for the message!";
    }

    // Endpoint för att visa bikupdata
    @GetMapping("/data")
    public String showClientData(Model model) {
        List<Double> weightData = dataService.getWeightData();
        model.addAttribute("weightData", weightData);
        /*
         * List<BeehiveViewModel> beehiveData = dataService.getAllBeehiveData();
         * model.addAttribute("beehiveData", beehiveData);
         */

        return "dataView";
    }
}
