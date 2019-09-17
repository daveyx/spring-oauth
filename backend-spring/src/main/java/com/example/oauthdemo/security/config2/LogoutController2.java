package com.example.oauthdemo.security.config2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogoutController2 {

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/oauth2/logout", method = RequestMethod.GET)
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
