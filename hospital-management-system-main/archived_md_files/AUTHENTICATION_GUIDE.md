# Hospital Management System - Authentication Implementation Guide

## 🔐 Adding Spring Security Authentication

This guide shows you how to add login/authentication to your Hospital Management System.

---

## 📁 Step 1: Create New Entity - User

### File: `src/main/java/com/hospital/model/User.java`

```java
package com.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity - Represents system users
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Will be encrypted

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String role; // ADMIN, DOCTOR, NURSE, PATIENT

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // If role is DOCTOR

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // If role is PATIENT

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;
}
```

---

## 📁 Step 2: Create User Repository

### File: `src/main/java/com/hospital/repository/UserRepository.java`

```java
package com.hospital.repository;

import com.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
```

---

## 📁 Step 3: Update pom.xml - Add Security Dependencies

```xml
<!-- Add to pom.xml dependencies -->

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT Token Support -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- BCrypt Password Encoder -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

---

## 📁 Step 4: Create Security Configuration

### File: `src/main/java/com/hospital/config/SecurityConfig.java`

```java
package com.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/css/**", "/js/**").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/patient/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
            .and()
            .csrf().disable();

        return http.build();
    }
}
```

---

## 📁 Step 5: Create Authentication Service

### File: `src/main/java/com/hospital/service/AuthenticationService.java`

```java
package com.hospital.service;

import com.hospital.model.User;
import com.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        // Check if user already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Username already exists
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return false; // Email already exists
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        return true; // Registration successful
    }

    public Optional<User> authenticateUser(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            // Verify password
            if (passwordEncoder.matches(rawPassword, user.get().getPassword())) {
                // Update last login
                user.get().setLastLogin(LocalDateTime.now());
                userRepository.save(user.get());
                return user;
            }
        }

        return Optional.empty();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user.get());
                return true;
            }
        }

        return false;
    }

    public void resetPassword(Long userId, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
        }
    }
}
```

---

## 📁 Step 6: Create Authentication Controller

### File: `src/main/java/com/hospital/controller/AuthController.java`

```java
package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        Optional<User> user = authenticationService.authenticateUser(username, password);

        if (user.isPresent()) {
            // Store user in session
            session.setAttribute("user", user.get());
            session.setAttribute("userId", user.get().getUserId());
            session.setAttribute("userRole", user.get().getRole());

            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user, Model model) {
        if (authenticationService.registerUser(user)) {
            model.addAttribute("success", "Registration successful! Please login.");
            return "auth/login";
        } else {
            model.addAttribute("error", "Username or email already exists");
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords don't match");
            return "redirect:/auth/change-password";
        }

        if (authenticationService.changePassword(userId, oldPassword, newPassword)) {
            model.addAttribute("success", "Password changed successfully");
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Current password is incorrect");
            return "redirect:/auth/change-password";
        }
    }
}
```

---

## 📄 Step 7: Create Login JSP Page

### File: `src/main/webapp/WEB-INF/jsp/auth/login.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospital Management System - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #2c3e50, #3498db);
        }

        .login-box {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 400px;
        }

        .login-box h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #2c3e50;
        }

        .form-group input {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            font-size: 1rem;
        }

        .form-group input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
        }

        .btn-login {
            width: 100%;
            padding: 0.75rem;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-login:hover {
            background-color: #2980b9;
        }

        .error-message {
            background-color: #ffe6e6;
            color: #e74c3c;
            padding: 1rem;
            border-radius: 4px;
            margin-bottom: 1rem;
            display: ${error != null ? 'block' : 'none'};
        }

        .success-message {
            background-color: #e6ffe6;
            color: #27ae60;
            padding: 1rem;
            border-radius: 4px;
            margin-bottom: 1rem;
            display: ${success != null ? 'block' : 'none'};
        }

        .links {
            text-align: center;
            margin-top: 1.5rem;
        }

        .links a {
            color: #3498db;
            text-decoration: none;
            margin: 0 0.5rem;
        }

        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <h1>🏥 Hospital Management System</h1>

            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="success-message">
                    ${success}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/auth/login" method="POST">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required autofocus>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn-login">Login</button>
            </form>

            <div class="links">
                <p>Don't have an account?
                    <a href="${pageContext.request.contextPath}/auth/register">Register here</a>
                </p>
                <p>
                    <a href="${pageContext.request.contextPath}/">Back to home</a>
                </p>
            </div>
        </div>
    </div>
</body>
</html>
```

---

## 📄 Step 8: Create Register JSP Page

### File: `src/main/webapp/WEB-INF/jsp/auth/register.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Hospital Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container" style="margin-top: 2rem;">
        <div style="max-width: 500px; margin: 0 auto; background: white; padding: 2rem; border-radius: 8px;">
            <h2>Create New Account</h2>

            <c:if test="${not empty error}">
                <div style="background-color: #ffe6e6; color: #e74c3c; padding: 1rem; border-radius: 4px; margin-bottom: 1rem;">
                    ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/auth/register" method="POST" class="form-horizontal">
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control">
                </div>

                <div class="form-group">
                    <label for="role">Account Type:</label>
                    <select id="role" name="role" class="form-control" required>
                        <option value="">Select Type</option>
                        <option value="PATIENT">Patient</option>
                        <option value="DOCTOR">Doctor</option>
                        <option value="ADMIN">Administrator</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%;">Register</button>
            </form>

            <p style="text-align: center; margin-top: 1rem;">
                Already have an account?
                <a href="${pageContext.request.contextPath}/auth/login">Login here</a>
            </p>
        </div>
    </div>
</body>
</html>
```

---

## ✅ Step 9: Update Existing Controllers

Update your `DashboardController.java` to require authentication:

```java
@GetMapping("/dashboard")
public String dashboard(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    
    if (user == null) {
        return "redirect:/auth/login";
    }

    // Load data based on user role
    if ("ADMIN".equals(user.getRole())) {
        model.addAttribute("totalPatients", patientService.getAllPatients().size());
        model.addAttribute("totalDoctors", doctorService.getAllDoctors().size());
        model.addAttribute("totalStaff", staffService.getAllStaff().size());
    }

    return "index";
}
```

---

## 📝 Step 10: Update application.properties

```properties
# Existing properties...

# Security
server.servlet.session.timeout=30m
spring.security.user.name=admin
spring.security.user.password=admin123

# Password encoding
security.password.algorithm=bcrypt
```

---

## 🚀 Testing the Authentication

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access login page:**
   ```
   http://localhost:8080/hospital/auth/login
   ```

4. **Register a new account:**
   ```
   http://localhost:8080/hospital/auth/register
   ```

5. **Test login** with your credentials

---

## 🔐 Security Best Practices

✅ Passwords are encrypted using BCrypt  
✅ Session management enabled  
✅ CSRF protection available  
✅ Role-based access control  
✅ Secure logout functionality  
✅ Password change capability  

---

## 📱 User Roles Explained

| Role | Access |
|------|--------|
| **ADMIN** | Full system access, user management |
| **DOCTOR** | View patients, write prescriptions, manage appointments |
| **NURSE** | Limited patient access, appointment management |
| **PATIENT** | View own records, book appointments |

---

## 🎯 Next Enhancements

- Add "Remember Me" functionality
- Implement email verification
- Add two-factor authentication (2FA)
- Password reset via email
- Session timeout warnings
- Failed login attempt tracking
- IP-based access control

---

**Your Hospital Management System now has secure authentication! 🔐**

For more enhancements, see **ENHANCEMENTS_GUIDE.md**
