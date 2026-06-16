package com.projects.ToDo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils; // Injects your token generator helper

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Read the hidden "Authorization" header sent by Postman
        String authHeader = request.getHeader("Authorization");

        // 2. Check if the header exists and starts with the word "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Cut out the word "Bearer " to extract the raw token

            // 3. Simple learning check: If a token exists, authenticate the request
            if (!token.isEmpty()) {
                // Create a temporary mock user profile for the server memory
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("learningUser", null, Collections.emptyList());

                // Unlock the security context door for this specific request!
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 4. Send the request onward to the controller
        filterChain.doFilter(request, response);
    }
}
