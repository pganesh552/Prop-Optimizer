package com.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BettingApplication.class, args);
    }
}
