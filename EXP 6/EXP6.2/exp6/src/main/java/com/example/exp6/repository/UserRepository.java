package com.example.exp6.repository;

import com.example.exp6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Normal query
    List<User> findAll();

    // Sort by ID
    List<User> findAllByOrderByIdAsc();

    // Sort by Name
    List<User> findAllByOrderByNameAsc();

    // Native SQL query
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAllNative();
}