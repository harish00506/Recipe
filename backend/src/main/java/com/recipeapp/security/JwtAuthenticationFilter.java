package com.recipeapp.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT Authentication Filter
 * Validates JWT tokens in request Authorization header and sets authentication context
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenService jwtTokenService;
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    
    /**
     * Filter requests to validate JWT tokens
     * Extracts token from Authorization header and validates it
     * Sets authentication context if token is valid
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Filter chain
     * @throws ServletException if filter error occurs
     * @throws IOException if I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = extractJwtFromRequest(request);
            
            if (jwt != null) {
                if (jwtTokenService.validateToken(jwt)) {
                    String username = jwtTokenService.extractUsername(jwt);
                    log.debug("Token validated for user: {}", username);
                    
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username, null, new ArrayList<>());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Security context set for user: {}", username);
                } else {
                    log.warn("Invalid or expired token");
                }
            }
        } catch (JwtException e) {
            log.debug("JWT token validation failed: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error processing authentication token", e);
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * Extract JWT token from Authorization header
     * Expected format: "Bearer <token>"
     *
     * @param request HTTP request
     * @return JWT token or null if not present/invalid
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        
        return null;
    }
}
