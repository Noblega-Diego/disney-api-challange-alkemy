package com.history.api.disney.services;

import com.history.api.disney.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static Long DURATION = 3600000L;

    private static String SECRET_KEY = "nadando3";

    private static String SUBJECT = "users/";

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(SUBJECT + user.getId())
                .claim("usr", user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + DURATION))
                .signWith(SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public String createToken(User user, Map<String, Object> values) {
        return Jwts.builder()
                .setClaims(values)
                .claim("usr", user.getEmail())
                .setSubject(SUBJECT + user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + DURATION))
                .signWith(SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Object getValue(String token, String key){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .get(key);
    }

    public boolean hasTokenExpired(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
    }

    public String extractUserName(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .get("usr", String.class);
    }

    public Long extractId(String token){
        String user = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(user.replace(SUBJECT, ""));
    }

    public boolean validateToken(String token, User user){
        return (!hasTokenExpired(token) &&
                extractUserName(token).equals(user.getEmail()) &&
                extractId(token).equals(user.getId()));
    }

}
