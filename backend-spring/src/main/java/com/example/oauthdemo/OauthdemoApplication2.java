package com.example.oauthdemo;

import com.example.oauthdemo.security.config2.SecurityConfig2Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = SecurityConfig2Filter.class)
        })
public class OauthdemoApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(OauthdemoApplication2.class, args);
    }
}

