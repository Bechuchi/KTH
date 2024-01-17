package com.bechuchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bechuchi.controller.*;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        try {
            // HelloController controller = new HelloController();
            Controller controller = new Controller();
            controller.scanLocalArea();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}