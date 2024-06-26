package com.github.elimxim.restfulex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DroneApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }
}
