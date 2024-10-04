package com.bechuchi.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.bechuchi.controller", "com.bechuchi.model", "com.bechuchi.startup" })
public class LaptopProgram {
    public static void main(String[] args) {
        SpringApplication.run(LaptopProgram.class, args);
    }
}