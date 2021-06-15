package com.example.oauthdemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;
import java.util.Objects;


@Configuration
public class TokenStoreConfig {

    @Value("${security.accessTokenValidity}")
    private int accessTokenValidity;

    @Value("${security.refreshTokenValidity}")
    private int refreshTokenValidity;

    @Value("${security.signing-key}")
    private String signingKey;

    private final TokenEnhancer tokenEnhancer;

    private final JdbcTemplate jdbcTemplate;


    public TokenStoreConfig(TokenEnhancer tokenEnhancer,
                            JdbcTemplate jdbcTemplate) {
        this.tokenEnhancer = tokenEnhancer;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(Objects.requireNonNull(jdbcTemplate.getDataSource()));
    }

    @Bean
    @Primary
    //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter()));

        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setAccessTokenValiditySeconds(accessTokenValidity);
        defaultTokenServices.setRefreshTokenValiditySeconds(refreshTokenValidity);
        defaultTokenServices.setTokenEnhancer(enhancerChain);
        return defaultTokenServices;
    }

}
