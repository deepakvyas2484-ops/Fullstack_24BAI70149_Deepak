package com.exp71.rbac.security;

import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiration;
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expiration) {
        this.key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secret)); this.expiration = expiration;
    }
    public String generateToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder().subject(username).claim("role", role).issuedAt(now)
                .expiration(new Date(now.getTime()+expiration)).signWith(key).compact();
    }
    public String extractUsername(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject(); }
    public boolean isValid(String token, String username) {
        try { var claims=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return username.equals(claims.getSubject()) && claims.getExpiration().after(new Date());
        } catch (Exception ex) { return false; }
    }
    public long getExpiration() { return expiration; }
}
