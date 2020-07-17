package com.example.oauthdemo2.authserver;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;


public class ClientCredentialsAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/oauth2/token", HttpMethod.POST.name());

    public ClientCredentialsAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (this.requestMatcher.matches(request)) {
            String[] credentials = extractBasicAuthenticationCredentials(request);
            String clientId = credentials[0];
            String clientSecret = credentials[1];

            OAuth2ClientAuthenticationToken authenticationToken = new OAuth2ClientAuthenticationToken(clientId, clientSecret);

            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private String[] extractBasicAuthenticationCredentials(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.toLowerCase().startsWith("basic ")) {
            return extractAndDecodeHeader(header, request);
        }
        throw new BadCredentialsException("Missing basic authentication header");
    }

    // Taken from BasicAuthenticationFilter (spring-security-web)
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) {

        byte[] base64Token = header.substring(6).getBytes(UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, getCredentialsCharset(request));

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    protected Charset getCredentialsCharset(HttpServletRequest httpRequest) {
        return UTF_8;
    }
}
