package ru.avdeev.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String salt;

    @Value("${jwt.expiration}")
    private String expirationTime;

    public Claims getClaims(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(salt.getBytes())
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        return getClaims(authToken)
                .getExpiration()
                .after(new Date());
    }
}
