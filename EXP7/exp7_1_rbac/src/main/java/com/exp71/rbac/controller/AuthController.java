package com.exp71.rbac.controller;

import com.exp71.rbac.dto.*;
import com.exp71.rbac.repository.UserRepository;
import com.exp71.rbac.security.JwtService;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager; private final JwtService jwtService; private final UserRepository users;
    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserRepository users) { this.authManager=authManager; this.jwtService=jwtService; this.users=users; }
    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            var user=users.findByUsername(request.username()).orElseThrow();
            return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user.getUsername(), user.getRole()), user.getUsername(), user.getRole(), jwtService.getExpiration()/1000));
        } catch (AuthenticationException ex) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(java.util.Map.of("message","Invalid username or password")); }
    }
}
