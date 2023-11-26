package com.example.blogapplication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenUtil {

    @Value("${security.jwt.token.secret-key}")
    private String jwtSecretKey;

    public Optional<Integer> extractRegIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.ofNullable(claims.get("reg_id", Integer.class));
        } catch (Exception e) {
            // Token is not valid r reg_id is missing
            return Optional.empty();
        }
    }
}
