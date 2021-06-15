package com.example.oauthdemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.REFRESH_TOKEN;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${security.grant-type}")
    private String grantType;

    @Value("${security.scope-read}")
    private String scopeRead;

    @Value("${security.scope-write}")
    private String scopeWrite = "write";

    @Value("${security.resource-ids}")
    private String resourceIds;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final DefaultTokenServices defaultTokenServices;

    private final AuthenticationManager authenticationManager;


    public AuthorizationServerConfig(PasswordEncoder passwordEncoder,
                                     UserDetailsService userDetailsService,
                                     DefaultTokenServices defaultTokenServices,
                                     AuthenticationManager authenticationManager) {
        this.defaultTokenServices = defaultTokenServices;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .authorizedGrantTypes(grantType, REFRESH_TOKEN)
                .scopes(scopeRead, scopeWrite)
                .resourceIds(resourceIds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenServices(defaultTokenServices)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService); // <-- this is mandatory for refresh_token

//                optional path mapping:
//                .pathMapping("/oauth/token", "/oauth1/token")
//                .pathMapping("/oauth/authorize", "/oauth1/authorize")
    }

}
