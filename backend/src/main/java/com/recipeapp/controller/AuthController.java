package com.recipeapp.controller;

import com.recipeapp.dto.AuthResponse;
import com.recipeapp.dto.LoginRequest;
import com.recipeapp.dto.RegisterRequest;
import com.recipeapp.exception.AuthenticationException;
import com.recipeapp.model.User;
import com.recipeapp.security.JwtTokenService;
import com.recipeapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Controller for authentication endpoints
 * Handles user registration and login
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    /**
     * Register a new user
     * POST /api/auth/register
     *
     * @param registerRequest registration details
     * @return AuthResponse with JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Registration request received for email: {}", registerRequest.getEmail());

        try {
            // Register user
            User user = userService.registerUser(
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getName()
            );

            // Generate JWT token
            String token = jwtTokenService.generateToken(user);
            Long expiresIn = jwtTokenService.getTokenExpirationInMs(token);

            // Build response
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .expiresIn(expiresIn)
                    .build();

            log.info("User registered successfully: {}", registerRequest.getEmail());
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

        } catch (IllegalArgumentException ex) {
            log.warn("Registration failed: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during registration", ex);
            throw new RuntimeException("Registration failed", ex);
        }
    }

    /**
     * Login user
     * POST /api/auth/login
     *
     * @param loginRequest login credentials
     * @return AuthResponse with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received for email: {}", loginRequest.getEmail());

        try {
            // Verify credentials
            User user = userService.verifyCredentials(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            // Generate JWT token
            String token = jwtTokenService.generateToken(user);
            Long expiresIn = jwtTokenService.getTokenExpirationInMs(token);

            // Build response
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .expiresIn(expiresIn)
                    .build();

            log.info("User logged in successfully: {}", loginRequest.getEmail());
            return ResponseEntity.ok(authResponse);

        } catch (AuthenticationException ex) {
            log.warn("Login failed: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during login", ex);
            throw new RuntimeException("Login failed", ex);
        }
    }

    /**
     * Refresh JWT token
     * POST /api/auth/refresh
     *
     * @param token current JWT token
     * @return new JWT token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        log.debug("Token refresh request received");

        try {
            // Extract token from "Bearer <token>"
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AuthenticationException("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);

            // Validate current token
            if (!jwtTokenService.validateToken(token)) {
                throw new AuthenticationException("Invalid or expired token");
            }

            // Refresh token
            String newToken = jwtTokenService.refreshToken(token);
            Long expiresIn = jwtTokenService.getTokenExpirationInMs(newToken);

            String email = jwtTokenService.extractUsername(newToken);
            User user = userService.getUserByEmail(email);

            AuthResponse authResponse = AuthResponse.builder()
                    .token(newToken)
                    .type("Bearer")
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .expiresIn(expiresIn)
                    .build();

            log.info("Token refreshed successfully for user: {}", email);
            return ResponseEntity.ok(authResponse);

        } catch (Exception ex) {
            log.error("Token refresh failed: {}", ex.getMessage());
            throw new AuthenticationException("Token refresh failed");
        }
    }

    /**
     * Validate JWT token
     * POST /api/auth/validate
     *
     * @param token JWT token
     * @return validation status
     */
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        log.debug("Token validation request received");

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.ok(false);
            }

            String token = authHeader.substring(7);
            boolean isValid = jwtTokenService.validateToken(token) && !jwtTokenService.isTokenExpired(token);

            log.debug("Token validation result: {}", isValid);
            return ResponseEntity.ok(isValid);

        } catch (Exception ex) {
            log.warn("Error during token validation: {}", ex.getMessage());
            return ResponseEntity.ok(false);
        }
    }
}
