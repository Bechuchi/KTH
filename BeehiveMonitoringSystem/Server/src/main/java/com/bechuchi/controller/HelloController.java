package com.bechuchi.controller;

import java.util.List;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("months", Arrays.asList("April", "Maj", "Juni", "Juli",
                "Augusti"));
        model.addAttribute("production", Arrays.asList(5, 25, 50, 85, 100));
        return "index"; // Namnet på din vy-fil, t.ex. index.html under
    }

}