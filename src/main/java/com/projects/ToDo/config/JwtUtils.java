package com.projects.ToDo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // 1. Generate a secure cryptographic signing key
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 2. Set the token expiration time (e.g., 24 hours in milliseconds)
    private final long jwtExpirationMs = 86400000;

    /**
     * Generates a signed JWT token string for a given username
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)                 // Set the owner of the token
                .setIssuedAt(now)                      // Set creation timestamp
                .setExpiration(expiryDate)            // Set expiration timestamp
                .signWith(secretKey)                   // Sign cryptographically with our secret key
                .compact();                            // Compress into the final string format
    }
}
