package ru.avdeev.service.user.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.avdeev.service.user.dto.UserDto;


import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String salt;

    @Value("${jwt.expiration}")
    private String expirationTime;

    public String generateToken(UserDto user) {

        HashMap<String, List<String>> claims = new HashMap<>();
        claims.put("roles", user.getRoles());

        long expirationSeconds = Long.parseLong(expirationTime);
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(salt.getBytes()))
                .compact();
    }
}
