package com.bechuchi.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * När main() körs, så startar Spring Boot.
 * Spring Boot skapar en huvudtråd (main thread) → denna är Spring Boot Application Context Thread.
 * Alla dina @Component (som NetworkController) skapas som beans och "levande objekt" av Spring Boot i denna huvudtråd.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.bechuchi.controller", "com.bechuchi.model", "com.bechuchi.startup",
        "com.bechuchi.service" })
public class Application {
    @Autowired
    public static void main(String[] args) {
        System.out.println("Beehive Application Open Again! again");
        SpringApplication.run(Application.class, args);
    }
}