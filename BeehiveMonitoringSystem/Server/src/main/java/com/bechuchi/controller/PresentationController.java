package com.bechuchi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import com.bechuchi.service.BeehiveDataService;

import java.util.List;

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
        List<BeehiveViewModel> beehiveData = dataService.getAllBeehiveData();
        model.addAttribute("beehiveData", beehiveData);

        return "dataView";
    }
}
