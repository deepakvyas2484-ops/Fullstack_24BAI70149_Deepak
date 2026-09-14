package com.example.experiment6.controller;

import com.example.experiment6.dto.PageResponse;
import com.example.experiment6.dto.UserRequest;
import com.example.experiment6.model.User;
import com.example.experiment6.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(
                userService.createUser(request)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "uid") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                userService.getUsers(pageable)
        );
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String uid) {

        userService.deleteUser(uid);

        return ResponseEntity.noContent().build();
    }
}