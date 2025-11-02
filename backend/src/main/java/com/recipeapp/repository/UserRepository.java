package com.recipeapp.repository;

import com.recipeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity
 * Provides CRUD and custom query operations for user management
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find user by email address
     * @param email the user's email
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if email already exists in the system
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find active user by email
     * @param email the user's email
     * @return Optional containing active user if found
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.isActive = true")
    Optional<User> findActiveByEmail(String email);

    /**
     * Check if active user with given email exists
     * @param email the email to check
     * @return true if active user exists with this email
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1 AND u.isActive = true")
    boolean existsActiveByEmail(String email);
}
