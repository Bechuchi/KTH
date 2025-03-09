package com.bechuchi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import com.bechuchi.service.BeehiveDataService;

import java.util.List;
import java.util.Map;

/*
 * The PresentationController is responsible for handling user interactions and
 * managing how data is presented in the UI. It retrieves processed data from
 * BeehiveDataService and sends it to the View.
 */
@Controller
public class PresentationController {
    private final BeehiveDataService beehiveDataService;

    @Autowired
    public PresentationController(BeehiveDataService beehiveDataService) {
        this.beehiveDataService = beehiveDataService;
    }

    @GetMapping("/console")
    @ResponseBody
    public String printToConsole() {
        System.out.println("Hello Again from Spring Boot!");
        return "Check Again console for the message!";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getBeehiveData(Model model) {
        Map<String, List<BeehiveViewModel>> listOfBeehives = beehiveDataService.getAllBeehiveData();
        System.out.println("**************************************");
        System.out.println("PresentationController: getBeehiveData()");
        System.out.println("returnerad data från getAllBeehiveData(): " + listOfBeehives);
        System.out.println("**************************************");
        System.out.println("Data skickas till UI, innehåll: " + listOfBeehives);

        for (Map.Entry<String, List<BeehiveViewModel>> entry : listOfBeehives.entrySet()) {
            System.out.println("MAC: " + entry.getKey());
            for (BeehiveViewModel vw : entry.getValue()) {
                System.out.println("BeehiveViewModel: " + vw);
                System.out.println("Weight values: " + vw.getWeightValues());
                System.out.println("WeightValues class: " + vw.getWeightValues().getClass().getName());
            }
        }

        model.addAttribute("beehiveData", listOfBeehives);
        return "dataView";
    }
}
