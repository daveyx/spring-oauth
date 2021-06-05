package com.example.oauthdemo.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class TokenEnhancer implements org.springframework.security.oauth2.provider.token.TokenEnhancer {

    public static final String USER_ID_KEY = "user_id";


    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        final Object userDetails = authentication.getPrincipal();

        if (userDetails != null && userDetails instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) userDetails;
            final Map<String, Object> additionalInfo;
            if (accessToken.getAdditionalInformation() == null || accessToken.getAdditionalInformation().isEmpty()) {
                additionalInfo = new HashMap<>();
            } else {
                additionalInfo = accessToken.getAdditionalInformation();
            }

            additionalInfo.put(USER_ID_KEY, myUserDetails.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }

        return accessToken;
    }

}
