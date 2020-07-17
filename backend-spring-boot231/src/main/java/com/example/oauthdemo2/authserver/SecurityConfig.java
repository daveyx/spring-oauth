package com.example.oauthdemo2.authserver;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwkSetEndpointFilter(generateJwkSet()), LogoutFilter.class);
    }

    private JWKSet generateJwkSet() throws JOSEException {
        JWK jwk = new RSAKeyGenerator(2048).keyID("minimal-ASA").keyUse(KeyUse.SIGNATURE).generate();
        return new JWKSet(jwk);
    }
}
