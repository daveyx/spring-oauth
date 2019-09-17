package com.example.oauthdemo;

import com.example.oauthdemo.security.config1.SecurityConfig1Filter;
import com.example.oauthdemo.security.config2.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SuppressWarnings("SpringBootApplicationSetup")
@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = SecurityConfig1Filter.class)
        })
public class OauthdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthdemoApplication.class, args);
    }
}

