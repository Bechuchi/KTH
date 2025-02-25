package com.bechuchi.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.bechuchi.controller", "com.bechuchi.model", "com.bechuchi.startup",
        "com.bechuchi.service" })
public class Application {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}