package com.example.oauthdemo.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LogoutController {

    private final TokenStore tokenStore;


    public LogoutController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @RequestMapping(value = "/oauth/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void logout(final HttpServletRequest request, @RequestBody String refreshToken) {
        OAuth2RefreshToken oAuth2RefreshToken = () -> refreshToken;
        tokenStore.removeRefreshToken(oAuth2RefreshToken);
    }

}
