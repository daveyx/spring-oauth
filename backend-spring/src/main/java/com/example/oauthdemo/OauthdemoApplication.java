package com.example.oauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringBootApplicationSetup")
@SpringBootApplication
//@ComponentScan(
//        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = SecurityConfig1Filter.class)
//        })
public class OauthdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthdemoApplication.class, args);
    }
}

