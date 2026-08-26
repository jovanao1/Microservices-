package com.example.meniservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MeniServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeniServiceApplication.class, args);
    }
}
