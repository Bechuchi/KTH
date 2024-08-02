package com.bechuchi.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bechuchi.controller.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            // HelloController controller = new HelloController();
            NetworkController controller = new NetworkController();
            controller.listenForIncomingNetworkTraffic();
        } catch (Exception e) {
            System.out.println("Server IP address could not be resolved.");
            e.printStackTrace();
        }
    }
}