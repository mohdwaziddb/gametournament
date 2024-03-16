package com.game.tournament.gametournament;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;


    @Component
    public class JwtUtil {

        private KeyStore keyStore;

        @PostConstruct
        public void init() throws Exception {
            try {
                keyStore = KeyStore.getInstance("JKS");
                InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
                keyStore.load(resourceAsStream, "secret".toCharArray());
            } catch (Exception e) {
                throw e;
            }
        }

        public String generateToken(Authentication authentication) throws Exception {
            User principal = (User) authentication.getPrincipal();
            return Jwts.builder()
                    .setSubject(principal.getUsername())
                    .signWith(getPrivateKey())
                    .compact();
        }

        private Key getPrivateKey() throws Exception {
            try {
                return (Key) keyStore.getKey("springblog", "secret".toCharArray());
            } catch (Exception e) {
                throw e;
            }
        }

        public String extractUsername(String token) throws Exception{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getPrivateKey()) // Assuming you also have a method to retrieve public key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        }
    }
    // Other methods for token validation, etc.

