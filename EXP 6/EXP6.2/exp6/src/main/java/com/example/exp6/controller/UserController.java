package com.example.exp6.controller;

import com.example.exp6.entity.User;
import com.example.exp6.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users - Normal Query
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Add User
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Normal Query
    @GetMapping("/normal")
    public Map<String, Object> normalQuery() {

        long start = System.nanoTime();

        List<User> users = userService.getAllUsers();

        long end = System.nanoTime();

        return createResponse(
                "NORMAL QUERY",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // JOIN FETCH
    @GetMapping("/join-fetch")
    public Map<String, Object> joinFetchQuery() {

        long start = System.nanoTime();

        List<User> users = userService.getJoinFetchUsers();

        long end = System.nanoTime();

        return createResponse(
                "JOIN FETCH",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // Cached Query
    @GetMapping("/cached")
    public Map<String, Object> cachedQuery() {

        long start = System.nanoTime();

        List<User> users = userService.getCachedUsers();

        long end = System.nanoTime();

        return createResponse(
                "CACHED QUERY",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // Native SQL
    @GetMapping("/native")
    public Map<String, Object> nativeQuery() {

        long start = System.nanoTime();

        List<User> users = userService.getNativeUsers();

        long end = System.nanoTime();

        return createResponse(
                "NATIVE SQL",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // Sort by ID
    @GetMapping("/sort/id")
    public Map<String, Object> sortById() {

        long start = System.nanoTime();

        List<User> users = userService.sortById();

        long end = System.nanoTime();

        return createResponse(
                "SORT BY ID",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // Sort by Name
    @GetMapping("/sort/name")
    public Map<String, Object> sortByName() {

        long start = System.nanoTime();

        List<User> users = userService.sortByName();

        long end = System.nanoTime();

        return createResponse(
                "SORT BY NAME",
                users,
                (end - start) / 1_000_000.0
        );
    }

    // Common response method
    private Map<String, Object> createResponse(
            String operation,
            List<User> users,
            double time
    ) {

        Map<String, Object> response = new HashMap<>();

        response.put("operation", operation);
        response.put("time", time);
        response.put("users", users);

        return response;
    }
}