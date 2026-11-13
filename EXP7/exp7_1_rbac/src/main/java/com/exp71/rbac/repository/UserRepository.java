package com.exp71.rbac.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exp71.rbac.entity.AppUser;
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
