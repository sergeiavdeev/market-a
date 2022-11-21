package ru.avdeev.marketsimpleapi.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.avdeev.marketsimpleapi.dto.UserDto;

import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String salt;

    @Value("${jwt.expiration}")
    private String expirationTime;

    public String extractUsername(String authToken) {

        return getClaims(authToken)
                .getSubject();
    }

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
    

    public String generateToken(UserDto user) {

        HashMap<String, List<String>> claims = new HashMap<>();
        // TODO
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

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            try {
                result.add(clazz.cast(o));
            } catch (ClassCastException e) {
                // log the exception or other error handling
            }
        }
        return result;
    }
}
