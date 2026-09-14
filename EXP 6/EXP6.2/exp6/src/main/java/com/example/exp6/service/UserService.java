package com.example.exp6.service;

import com.example.exp6.entity.User;
import com.example.exp6.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Normal query
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // JOIN FETCH / optimized query demonstration
    public List<User> getJoinFetchUsers() {
        return userRepository.findAll();
    }

    // Cached query
    @Cacheable("users")
    public List<User> getCachedUsers() {
        return userRepository.findAll();
    }

    // Native SQL query
    public List<User> getNativeUsers() {
        return userRepository.findAllNative();
    }

    // Sort by ID
    public List<User> sortById() {
        return userRepository.findAllByOrderByIdAsc();
    }

    // Sort by Name
    public List<User> sortByName() {
        return userRepository.findAllByOrderByNameAsc();
    }

    // Add user
    @CacheEvict(value = "users", allEntries = true)
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Delete user
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}