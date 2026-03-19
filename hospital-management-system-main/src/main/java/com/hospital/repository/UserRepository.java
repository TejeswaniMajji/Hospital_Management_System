package com.hospital.repository;

import com.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Handles database operations for user authentication and management
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find all users by role
     */
    List<User> findByRole(String role);

    /**
     * Find all users by status
     */
    List<User> findByStatus(String status);

    /**
     * Find all active users
     */
    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findAllActiveUsers();

    /**
     * Find recently logged in users (last 7 days)
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :sevenDaysAgo ORDER BY u.lastLogin DESC")
    List<User> findRecentlyLoggedInUsers(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    /**
     * Find users by first name (case-insensitive)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))")
    List<User> findByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);

    /**
     * Find users by last name (case-insensitive)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
    List<User> findByLastNameContainingIgnoreCase(@Param("lastName") String lastName);

    /**
     * Count users by role
     */
    long countByRole(String role);

    /**
     * Count active users
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.status = 'ACTIVE'")
    long countActiveUsers();
}
