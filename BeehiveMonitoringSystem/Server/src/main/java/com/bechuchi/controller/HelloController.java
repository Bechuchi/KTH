package com.bechuchi.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    /*
     * @GetMapping("/")
     * public String index() {
     * return "Hello from Spring Boot!";
     * }
     */

    @GetMapping
    public String index(Model model) {
        model.addAttribute("message", "Hello from Spring Boot!");
        return "index"; // Returnerar vyn (index.html)
    }
}
