package com.example.gaoyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GaoyuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaoyuanApplication.class, args);
    }

}
