package com.example.oauthdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class OauthdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(OauthdemoApplication.class, args);
    }

    @RequestMapping("/api/resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> home() {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Secured World");
        return model;
    }

    @RequestMapping("/api/public-resource")
    @CrossOrigin(origins = "*")
    public Map<String, Object> publicHome() {
        final Map<String, Object> model = new HashMap<>();
        model.put("content", "Hello unsecured World");
        return model;
    }

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/oauth/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(final HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            final String tokenValue = authHeader.replace("Bearer", "").trim();
            final OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}

