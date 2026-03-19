package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Authentication Controller for Hospital Management System
 * Handles login, registration, logout, and password management
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Show login form
     */
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String logout,
                                @RequestParam(required = false) String expired,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("success", "You have been logged out successfully");
        }
        if (expired != null) {
            model.addAttribute("error", "Your session has expired. Please login again");
        }
        return "auth/login";
    }

    /**
     * Show registration form
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    /**
     * Process registration request
     */
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user,
                                     @RequestParam String confirmPassword,
                                     Model model) {
        
        // Validate passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Validate password strength
        if (!isPasswordStrong(user.getPassword())) {
            model.addAttribute("error", "Password must be at least 8 characters and contain uppercase, lowercase, and numbers");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Set default values
        user.setRole("PATIENT"); // Default role is patient
        user.setStatus("ACTIVE");

        // Register user
        if (authenticationService.registerUser(user)) {
            model.addAttribute("success", "Registration successful! Please login with your credentials");
            return "auth/login";
        } else {
            model.addAttribute("error", "Username or email already exists. Please try different credentials");
            model.addAttribute("user", user);
            return "auth/register";
        }
    }

    /**
     * Logout user
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout=true";
    }

    /**
     * Show change password form
     */
    @GetMapping("/change-password")
    public String showChangePasswordForm(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/auth/login";
        }
        return "auth/change-password";
    }

    /**
     * Process change password request
     */
    @PostMapping("/change-password")
    public String processChangePassword(@RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       HttpSession session,
                                       Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/auth/login";
        }

        // Validate new passwords match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match");
            return "auth/change-password";
        }

        // Validate password strength
        if (!isPasswordStrong(newPassword)) {
            model.addAttribute("error", "Password must be at least 8 characters and contain uppercase, lowercase, and numbers");
            return "auth/change-password";
        }

        // Check if old password is same as new
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", "New password must be different from old password");
            return "auth/change-password";
        }

        // Change password
        if (authenticationService.changePassword(userId, oldPassword, newPassword)) {
            model.addAttribute("success", "Password changed successfully. Please login again");
            session.invalidate();
            return "redirect:/auth/login";
        } else {
            model.addAttribute("error", "Current password is incorrect");
            return "auth/change-password";
        }
    }

    /**
     * Validate password strength
     * Must be at least 8 characters, contain uppercase, lowercase, and numbers
     */
    private boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasNumbers = password.matches(".*\\d.*");

        return hasUppercase && hasLowercase && hasNumbers;
    }

    /**
     * Show user profile
     */
    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/auth/login";
        }

        Optional<User> updatedUser = authenticationService.getUserById(user.getUserId());
        if (updatedUser.isPresent()) {
            model.addAttribute("user", updatedUser.get());
        }

        return "auth/profile";
    }

    /**
     * Update user profile
     */
    @PostMapping("/profile/update")
    public String updateUserProfile(@RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String phoneNumber,
                                   HttpSession session,
                                   Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/auth/login";
        }

        User updatedUser = new User();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setPhoneNumber(phoneNumber);

        authenticationService.updateUserProfile(userId, updatedUser);

        // Update session
        Optional<User> user = authenticationService.getUserById(userId);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            session.setAttribute("fullName", user.get().getFullName());
            model.addAttribute("success", "Profile updated successfully");
        }

        return "redirect:/auth/profile";
    }

    /**
     * Show forgot password form
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    /**
     * Process forgot password request
     */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        Optional<User> user = authenticationService.getUserByEmail(email);

        if (user.isPresent()) {
            // In a real application, send password reset email
            // For now, just show success message
            model.addAttribute("success", "If this email exists, you will receive password reset instructions");
            return "redirect:/auth/forgot-password?success=true";
        } else {
            return "redirect:/auth/forgot-password?error=true";
        }
    }
}
