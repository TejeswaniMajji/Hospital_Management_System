package com.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * User Entity - Represents system users with authentication and role-based access
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt encrypted

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String role; // ADMIN, DOCTOR, NURSE, PATIENT

    @Column(nullable = false, length = 20)
    private String status; // ACTIVE, INACTIVE, SUSPENDED

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // If role is DOCTOR

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // If role is PATIENT

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    private LocalDateTime lastPasswordChange;

    private Integer failedLoginAttempts = 0;

    private LocalDateTime accountLockedUntil;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
        this.failedLoginAttempts = 0;
    }

    public User() {}

    public User(Long userId, String username, String email, String password, String firstName, String lastName, String role, String status, String phoneNumber, Doctor doctor, Patient patient, LocalDateTime createdAt, LocalDateTime lastLogin, LocalDateTime lastPasswordChange, Integer failedLoginAttempts, LocalDateTime accountLockedUntil) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.doctor = doctor;
        this.patient = patient;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.lastPasswordChange = lastPasswordChange;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountLockedUntil = accountLockedUntil;
    }

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    public LocalDateTime getLastPasswordChange() { return lastPasswordChange; }
    public void setLastPasswordChange(LocalDateTime lastPasswordChange) { this.lastPasswordChange = lastPasswordChange; }
    public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    public LocalDateTime getAccountLockedUntil() { return accountLockedUntil; }
    public void setAccountLockedUntil(LocalDateTime accountLockedUntil) { this.accountLockedUntil = accountLockedUntil; }

    /**
     * Get full name of user
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Check if account is locked
     */
    public boolean isAccountLocked() {
        if (accountLockedUntil == null) {
            return false;
        }
        if (LocalDateTime.now().isAfter(accountLockedUntil)) {
            accountLockedUntil = null;
            failedLoginAttempts = 0;
            return false;
        }
        return true;
    }
}


