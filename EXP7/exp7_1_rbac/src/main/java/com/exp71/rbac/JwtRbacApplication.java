package com.exp71.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.exp71.rbac.entity.AppUser;
import com.exp71.rbac.repository.UserRepository;

@SpringBootApplication
public class JwtRbacApplication {
    public static void main(String[] args) { SpringApplication.run(JwtRbacApplication.class, args); }

    @Bean
    CommandLineRunner seedUsers(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            add(users, encoder, "Deepak", "24BAI70149", "ADMIN");
            add(users, encoder, "editor", "editor123", "EDITOR");
            add(users, encoder, "viewer", "viewer123", "VIEWER");
        };
    }
    private void add(UserRepository users, PasswordEncoder encoder, String username, String password, String role) {
        if (!users.existsByUsername(username)) users.save(new AppUser(username, encoder.encode(password), role));
    }
}
