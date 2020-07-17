package com.example.oauthdemo2.authserver;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class JwkSetEndpointFilter extends OncePerRequestFilter {

    static final String DEFAULT_JWK_SET_URI = "/oauth2/jwks";
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(DEFAULT_JWK_SET_URI, GET.name());
    private final JWKSet jwkSet;

    public JwkSetEndpointFilter(JWKSet jwkSet) {
        Assert.notNull(jwkSet, "jwkSet cannot be null");
        this.jwkSet = jwkSet;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (matchesRequest(request)) {
            respond(response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void respond(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        try (Writer writer = response.getWriter()) {
            writer.write(this.jwkSet.toPublicJWKSet().toJSONObject().toJSONString());
        }
    }

    private boolean matchesRequest(HttpServletRequest request) {
        return this.requestMatcher.matches(request);
    }
}
