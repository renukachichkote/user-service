package com.example.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = obtainUsername(request);
            String password = obtainPassword(request);

            //// Create an authentication token
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            // Perform authentication
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Username or password");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authentication) {
        // Generate a JWT token
        jwtService.createAuthResponse(authentication);
    }
}
