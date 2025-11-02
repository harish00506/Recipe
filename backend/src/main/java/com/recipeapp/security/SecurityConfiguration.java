package com.recipeapp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security Configuration
 * Configures JWT authentication, CORS, and endpoint protection
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    /**
     * Configure HTTP security with JWT filter and CORS
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain with JWT authentication");
        
        http
            .csrf().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests(authz -> authz
                    // Public endpoints - no authentication required
                    .requestMatchers(
                            "/api/auth/register",
                            "/api/auth/login",
                            "/api/auth/refresh",
                            "/api/health",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()
                    
                    // Protected endpoints - require authentication
                    .requestMatchers(
                            "/api/recipes/**",
                            "/api/cuisines/**",
                            "/api/ingredients/**",
                            "/api/shopping-lists/**"
                    ).authenticated()
                    
                    // All other requests require authentication
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.warn("Unauthorized access attempt: {}", authException.getMessage());
                    response.sendError(401, "Unauthorized");
                });
        
        log.info("SecurityFilterChain configured successfully");
        return http.build();
    }
    
    /**
     * Configure CORS to allow frontend access
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Configuring CORS");
        
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",      // React development
                "http://localhost:5173",      // Vite development
                "http://127.0.0.1:3000",
                "http://127.0.0.1:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        log.info("CORS configured for: localhost:3000, localhost:5173");
        return source;
    }
    
    /**
     * Password encoder bean using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Authentication manager bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
