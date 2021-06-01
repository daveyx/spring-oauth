package com.example.oauthdemo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Controller {

    @RequestMapping("/api1/resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> resource() {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World 1 from resource1");
        model.put("serverTime", LocalDateTime.now().toString());
        return model;
    }

    @RequestMapping("/api1/resource2")
    @CrossOrigin(origins = "*")
    public Map<String, Object> resource2() {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World 1 from resource2");
        model.put("serverTime", LocalDateTime.now().toString());
        return model;
    }

    @RequestMapping("/api1/public-resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> publicHome() {
        final Map<String, Object> model = new HashMap<>();
        model.put("content", "Hello unsecured World");
        return model;
    }

}
