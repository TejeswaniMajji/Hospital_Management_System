package com.hospital.controller;

import com.hospital.config.JwtUtil;
import com.hospital.model.User;
import com.hospital.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Issues JWTs for stateless REST API clients. Mirrors the AuthController/AuthApiController
 * pairing used elsewhere in this codebase (e.g. AppointmentController/AppointmentApiController) —
 * the existing /auth/** form-login flow for the Thymeleaf web pages is untouched.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> authenticated = authenticationService.authenticateUser(username, password);
        if (authenticated.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        User user = authenticated.get();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("expiresInMs", jwtUtil.getExpirationMs());
        return ResponseEntity.ok(response);
    }
}
