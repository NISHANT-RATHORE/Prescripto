package com.example.appointmentservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String secret;

   public boolean validateToken(String token) {
       try {
           Claims claims = Jwts.parser()
                   .setSigningKey(secret)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
           System.out.println("Token is valid. Claims: " + claims); // Debugging log
           return claims != null;
       } catch (Exception e) {
           System.out.println("Token validation error: " + e.getMessage()); // Debugging log
           return false;
       }
   }

    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}