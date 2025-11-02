package com.recipeapp.security;

import com.recipeapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for JWT token generation, validation, and parsing
 * Handles all JWT operations for authentication
 */
@Service
@Slf4j
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * Generate JWT token for authenticated user
     * @param user the authenticated user
     * @return JWT token string
     */
    public String generateToken(User user) {
        log.debug("Generating JWT token for user: {}", user.getEmail());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());

        String token = createToken(claims, user.getEmail());
        log.info("JWT token generated successfully for user: {}", user.getEmail());

        return token;
    }

    /**
     * Create JWT token with claims
     * @param claims token claims
     * @param subject token subject (email)
     * @return JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validate JWT token
     * @param token JWT token string
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            log.debug("Validating JWT token");
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.debug("JWT token validation successful");
            return true;
        } catch (Exception e) {
            log.warn("JWT token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extract username (email) from token
     * @param token JWT token string
     * @return username/email from token
     */
    public String extractUsername(String token) {
        log.debug("Extracting username from JWT token");
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * Extract user ID from token
     * @param token JWT token string
     * @return user ID as string
     */
    public String extractUserId(String token) {
        log.debug("Extracting user ID from JWT token");
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get("userId");
    }

    /**
     * Extract user name from token
     * @param token JWT token string
     * @return user name
     */
    public String extractUserName(String token) {
        log.debug("Extracting user name from JWT token");
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get("name");
    }

    /**
     * Check if token is expired
     * @param token JWT token string
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            log.warn("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }

    /**
     * Get expiration date from token
     * @param token JWT token string
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * Get all claims from token
     * @param token JWT token string
     * @return claims object
     */
    private Claims getAllClaimsFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error extracting claims from token: {}", e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }

    /**
     * Refresh token (generate new token with same claims)
     * @param token current JWT token
     * @return new JWT token
     */
    public String refreshToken(String token) {
        try {
            log.debug("Refreshing JWT token");
            Claims claims = getAllClaimsFromToken(token);
            
            Map<String, Object> newClaims = new HashMap<>(claims);
            newClaims.remove("exp");
            newClaims.remove("iat");
            
            String refreshedToken = createToken(newClaims, claims.getSubject());
            log.info("JWT token refreshed successfully");
            
            return refreshedToken;
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            throw new RuntimeException("Token refresh failed", e);
        }
    }

    /**
     * Extract expiration time remaining in milliseconds
     * @param token JWT token string
     * @return milliseconds until expiration
     */
    public Long getTokenExpirationInMs(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            long timeRemaining = expiration.getTime() - new Date().getTime();
            return Math.max(timeRemaining, 0);
        } catch (Exception e) {
            log.warn("Error calculating token expiration time: {}", e.getMessage());
            return 0L;
        }
    }
}
