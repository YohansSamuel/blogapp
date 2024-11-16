package com.yohans.blog.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "YA78#%jYA78#%ji@!4i@!YA7YA78#%YA78#%ji@!4YA78#%ji@!4ji@!48#%ji@!44"; // This should be stored securely (e.g., environment variable)

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set expiration time (1 hour)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
