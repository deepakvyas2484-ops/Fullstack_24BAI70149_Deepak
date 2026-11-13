package com.exp72.securetokens.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Service
public class TokenService {
    private final SecretKey key;
    private final long accessMillis;
    private final long refreshMillis;

    public TokenService(
            @Value("${app.jwt.secret-base64}") String secret,
            @Value("${app.jwt.access-minutes}") long accessMinutes,
            @Value("${app.jwt.refresh-days}") long refreshDays) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.accessMillis = accessMinutes * 60_000L;
        this.refreshMillis = refreshDays * 24L * 60 * 60 * 1000;
    }

    public String createAccessToken(String username) {
        return createToken(username, "ACCESS", accessMillis);
    }

    public String createRefreshToken(String username) {
        return createToken(username, "REFRESH", refreshMillis);
    }

    private String createToken(String username, String type, long lifetime) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("type", type)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + lifetime))
                .signWith(key)
                .compact();
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
    }

    public long getAccessMillis() { return accessMillis; }
    public long getRefreshMillis() { return refreshMillis; }
}
