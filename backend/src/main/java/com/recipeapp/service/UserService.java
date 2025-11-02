package com.recipeapp.service;

import com.recipeapp.exception.AuthenticationException;
import com.recipeapp.exception.ResourceNotFoundException;
import com.recipeapp.model.User;
import com.recipeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for user-related operations
 * Handles user registration, login, and profile management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * @param email user's email
     * @param password user's password (will be encoded)
     * @param name user's full name
     * @return created User
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional
    public User registerUser(String email, String password, String name) {
        log.info("Attempting to register new user with email: {}", email);

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            log.warn("Registration failed: email {} already exists", email);
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user with encoded password
        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .name(name)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User successfully registered: {}", email);

        return savedUser;
    }

    /**
     * Find user by ID
     * @param userId the user's ID
     * @return User if found
     * @throws ResourceNotFoundException if user not found
     */
    public User getUserById(UUID userId) {
        log.debug("Fetching user by ID: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    /**
     * Find user by email
     * @param email user's email
     * @return User if found
     * @throws ResourceNotFoundException if user not found
     */
    public User getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    /**
     * Verify user credentials during login
     * @param email user's email
     * @param password user's password (plain text)
     * @return User if credentials are valid
     * @throws AuthenticationException if credentials are invalid
     */
    public User verifyCredentials(String email, String password) {
        log.debug("Verifying credentials for user: {}", email);

        User user = userRepository.findActiveByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login failed: user not found or inactive: {}", email);
                    return new AuthenticationException("Invalid email or password");
                });

        // Verify password
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            log.warn("Login failed: invalid password for user: {}", email);
            throw new AuthenticationException("Invalid email or password");
        }

        log.info("Credentials verified successfully for user: {}", email);
        return user;
    }

    /**
     * Update user profile
     * @param userId user's ID
     * @param name new name (nullable)
     * @return updated User
     * @throws ResourceNotFoundException if user not found
     */
    @Transactional
    public User updateUserProfile(UUID userId, String name) {
        log.debug("Updating user profile: {}", userId);

        User user = getUserById(userId);

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }

        User updatedUser = userRepository.save(user);
        log.info("User profile updated: {}", userId);

        return updatedUser;
    }

    /**
     * Change user password
     * @param userId user's ID
     * @param oldPassword current password
     * @param newPassword new password
     * @throws ResourceNotFoundException if user not found
     * @throws AuthenticationException if old password is incorrect
     */
    @Transactional
    public void changePassword(UUID userId, String oldPassword, String newPassword) {
        log.debug("Changing password for user: {}", userId);

        User user = getUserById(userId);

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            log.warn("Password change failed: invalid old password for user: {}", userId);
            throw new AuthenticationException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", userId);
    }

    /**
     * Deactivate user account (soft delete)
     * @param userId user's ID
     * @throws ResourceNotFoundException if user not found
     */
    @Transactional
    public void deactivateUser(UUID userId) {
        log.debug("Deactivating user: {}", userId);

        User user = getUserById(userId);
        user.setIsActive(false);
        userRepository.save(user);

        log.info("User deactivated: {}", userId);
    }

    /**
     * Check if email is available
     * @param email email to check
     * @return true if email is available, false if already registered
     */
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    /**
     * Check if active user exists with given email
     * @param email email to check
     * @return true if active user exists with this email
     */
    public boolean isActiveUserExists(String email) {
        return userRepository.existsActiveByEmail(email);
    }
}
