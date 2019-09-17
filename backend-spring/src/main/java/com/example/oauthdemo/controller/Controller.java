package com.example.oauthdemo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Controller {

    @RequestMapping("/api1/resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> home() {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World 1");
        return model;
    }

    @RequestMapping("/api1/public-resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> publicHome() {
        final Map<String, Object> model = new HashMap<>();
        model.put("content", "Hello unsecured World");
        return model;
    }

    @RequestMapping("/api2/resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> home2() {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World 2");
        return model;
    }

}
