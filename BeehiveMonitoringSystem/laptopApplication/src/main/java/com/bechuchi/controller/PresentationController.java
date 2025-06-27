package com.bechuchi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bechuchi.model.PacketHandler;
import com.bechuchi.model.ViewModel.BeehiveViewModel;
import java.util.List;
import java.util.Map;

/*
 * The PresentationController is responsible for handling user interactions and
 * managing how data is presented in the UI. It retrieves processed data from
 * BeehiveDataService and sends it to the View.
 */
@Controller
public class PresentationController {
    private final PacketHandler packetHandler;

    @Autowired
    public PresentationController(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    @GetMapping("/console")
    @ResponseBody
    public String printToConsole() {
        return "Check Again console for the message!";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getBeehiveData(Model model) {
        Map<String, List<BeehiveViewModel>> listOfBeehives = packetHandler.getAllBeehiveData();
        model.addAttribute("beehiveData", listOfBeehives);

        return "dataView";
    }
}
