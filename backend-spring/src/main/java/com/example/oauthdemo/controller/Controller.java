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

    @RequestMapping("/api/resource")
    @CrossOrigin(origins = "*")
    public Map<String, String> resource() {
        return getSecuredContent(1);
    }

    @RequestMapping("/api/resource2")
    @CrossOrigin(origins = "*")
    public Map<String, String> resource2() {
        return getSecuredContent(2);
    }

    @RequestMapping("/api/public-resource")
    @CrossOrigin(origins = "*")
    public Map<String, String> publicHome() {
        return getPublicContent();
    }

    public static Map<String, String> getPublicContent() {
        final Map<String, String> model = new HashMap<>();
        model.put("content", "Hello unsecured World");
        return model;
    }

    public static Map<String, String> getSecuredContent(int resource) {
        final Map<String, String> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World from resource" + resource);
        model.put("serverTime", LocalDateTime.now().toString());

        return model;
    }

}
