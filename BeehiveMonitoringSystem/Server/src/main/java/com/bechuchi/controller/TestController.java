package com.bechuchi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The TestController class serves a dual purpose in the Beehive Monitoring
 * System architecture.
 * It is designed to handle both direct server-side logging for debugging
 * purposes and the rendering of views to the client.
 *
 * Methods in this controller are equipped with @ResponseBody to return data
 * directly to the client,
 * which is useful for debugging and direct interactions without the need for a
 * view.
 * This setup provides a flexible way to test and validate server responses
 * during development.
 *
 * @GetMapping("/console") is used to print debug messages directly to the
 * console from web requests,
 * supporting rapid development and troubleshooting without disrupting the user
 * interface workflow.
 *
 * @GetMapping("/") handles the main page view rendering, facilitating both
 * development testing
 * and end-user interaction by displaying formatted data from beehives in a
 * user-friendly format.
 */
@Controller
public class TestController {
    @GetMapping("/console")
    @ResponseBody
    public String printToConsole() {
        System.out.println("Hello from Spring Boot!");
        return "Check console for the message!";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Spring Boot!");
        return "index"; // Namnet på din vy-fil, t.ex. index.html under src/main/resources/templates
    }
}
