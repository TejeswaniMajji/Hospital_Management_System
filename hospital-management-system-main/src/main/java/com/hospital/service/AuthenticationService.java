package com.hospital.service;

import com.hospital.model.User;
import com.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Authentication Service for Hospital Management System
 * Handles user registration, login, password management, and account security
 * Implements UserDetailsService for Spring Security integration
 */
@Service
@Transactional
@SuppressWarnings("null")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 15;

    /**
     * Register a new user
     * @param user User object with registration details
     * @return true if registration successful, false if username/email already exists
     */
    public boolean registerUser(User user) {
        // Validate input
        if (user == null || user.getUsername() == null || user.getEmail() == null) {
            return false;
        }

        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        // Encrypt password using BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setFailedLoginAttempts(0);

        userRepository.save(user);
        return true;
    }

    /**
     * Authenticate user with username and password
     * Includes account lock mechanism after failed attempts
     * @param username Username
     * @param rawPassword Raw password (not encrypted)
     * @return User object if authentication successful, empty Optional if failed
     */
    public Optional<User> authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // Check if account is locked
        if (user.isAccountLocked()) {
            return Optional.empty();
        }

        // Check if account is active
        if (!"ACTIVE".equals(user.getStatus())) {
            return Optional.empty();
        }

        // Verify password
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            // Login successful - reset failed attempts
            user.setFailedLoginAttempts(0);
            user.setAccountLockedUntil(null);
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            return Optional.of(user);
        } else {
            // Login failed - increment failed attempts
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            // Lock account if max attempts exceeded
            if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
                user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(LOCK_TIME_MINUTES));
            }

            userRepository.save(user);
            return Optional.empty();
        }
    }

    /**
     * Get user by username
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get user by ID
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Change password for existing user
     * @param userId User ID
     * @param oldPassword Old password (not encrypted)
     * @param newPassword New password (will be encrypted)
     * @return true if password changed successfully, false if old password is incorrect
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastPasswordChange(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    /**
     * Reset password (admin function)
     * @param userId User ID
     * @param newPassword New password (will be encrypted)
     */
    public void resetPassword(Long userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setLastPasswordChange(LocalDateTime.now());
            user.setFailedLoginAttempts(0);
            user.setAccountLockedUntil(null);
            userRepository.save(user);
        }
    }

    /**
     * Unlock user account (admin function)
     */
    public void unlockUserAccount(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFailedLoginAttempts(0);
            user.setAccountLockedUntil(null);
            userRepository.save(user);
        }
    }

    /**
     * Deactivate user account
     */
    public void deactivateUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("INACTIVE");
            userRepository.save(user);
        }
    }

    /**
     * Activate user account
     */
    public void activateUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("ACTIVE");
            userRepository.save(user);
        }
    }

    /**
     * Get all users by role
     */
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    /**
     * Get all active users
     */
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    /**
     * Get count of users by role
     */
    public long getUserCountByRole(String role) {
        return userRepository.countByRole(role);
    }

    /**
     * Update user profile information
     */
    public User updateUserProfile(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updatedUser.getFirstName() != null) {
                user.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                user.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getPhoneNumber() != null) {
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            return userRepository.save(user);
        }

        return null;
    }

    /**
     * Load user details for Spring Security authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
