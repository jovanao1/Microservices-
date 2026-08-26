package com.example.rezervacijaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RezervacijaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RezervacijaServiceApplication.class, args);
    }

}
