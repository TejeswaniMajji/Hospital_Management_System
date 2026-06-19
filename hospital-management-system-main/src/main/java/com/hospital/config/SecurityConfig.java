package com.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security Configuration for Hospital Management System
 * Handles authentication, authorization, and request filtering
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    /**
     * Password encoder using BCrypt algorithm
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Strength 12 for better security
    }

    /**
     * Authentication manager bean
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    /**
     * Security filter chain configuration
     * Defines URL access rules, login flow, logout handling, and CSRF protection
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        // Authorization rules using Spring Security 6 API with Lambda DSL
        http.authorizeHttpRequests(authorize -> authorize
            // Public endpoints - accessible without authentication
            .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
            // JWT login for stateless REST API clients (issues the token; everything else under
            // /api/** still falls through to anyRequest().authenticated() below, which accepts
            // EITHER a session cookie OR a valid JWT set by JwtAuthenticationFilter)
            .requestMatchers(new AntPathRequestMatcher("/api/auth/login", "POST")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/css/**"), new AntPathRequestMatcher("/js/**"), 
                           new AntPathRequestMatcher("/images/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/error"), new AntPathRequestMatcher("/error/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

            // Restricted endpoints - Using ROLE_ prefix as added in UserDetailsService
            .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
            .requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyRole("ADMIN", "USER")

            // All other requests require authentication
            .anyRequest().authenticated()
        )
        // Form login configuration using Lambda DSL
        .formLogin(form -> form
            .loginPage("/auth/login")
            .loginProcessingUrl("/auth/login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/auth/login?error=true")
            .permitAll()
        )
        // Logout configuration using Lambda DSL
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
            .logoutSuccessUrl("/auth/login?logout=true")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
        )
        // CSRF protection using Lambda DSL
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"), 
                                    new AntPathRequestMatcher("/auth/**"),
                                    new AntPathRequestMatcher("/api/**"),
                                    new AntPathRequestMatcher("/doctor/**"),
                                    new AntPathRequestMatcher("/patient/**"),
                                    new AntPathRequestMatcher("/appointment/**"),
                                    new AntPathRequestMatcher("/features/**"))
        )
        // Headers configuration for H2 console
        .headers(headers -> headers.frameOptions(frame -> frame.disable()))
        // JWT filter runs ahead of session-based auth but only acts when no
        // authentication is already present (see JwtAuthenticationFilter) - additive,
        // not a replacement for the existing form-login/session flow.
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

