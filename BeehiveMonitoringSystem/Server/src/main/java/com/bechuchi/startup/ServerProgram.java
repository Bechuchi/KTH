package com.bechuchi.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.bechuchi.controller", "com.bechuchi.model", "com.bechuchi.startup" })
public class ServerProgram {
    public static void main(String[] args) {
        SpringApplication.run(ServerProgram.class, args);
    }
}