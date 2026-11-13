package com.exp72.securetokens.controller;

import com.exp72.securetokens.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/generate")
    public Map<String, Object> generate(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        return tokenResponse(username);
    }

    @PostMapping("/refresh")
    public Map<String, Object> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token is required.");
        }
        try {
            Claims claims = tokenService.parseAndValidate(refreshToken);
            if (!"REFRESH".equals(claims.get("type", String.class))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A refresh token is required.");
            }
            return Map.of("username", claims.getSubject(),
                    "accessToken", tokenService.createAccessToken(claims.getSubject()),
                    "accessTokenLifetime", "15 minutes",
                    "message", "New access token generated successfully.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Refresh token is invalid or expired.");
        }
    }

    private Map<String, Object> tokenResponse(String username) {
        return Map.of("username", username,
                "accessToken", tokenService.createAccessToken(username),
                "refreshToken", tokenService.createRefreshToken(username),
                "accessTokenLifetime", "15 minutes",
                "refreshTokenLifetime", "7 days",
                "message", "Access and refresh tokens generated successfully.");
    }
}
