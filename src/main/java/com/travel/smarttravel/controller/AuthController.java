package com.travel.smarttravel.controller;

import com.travel.smarttravel.dto.AuthRequest;
import com.travel.smarttravel.dto.AuthResponse;
import com.travel.smarttravel.entity.User;
import com.travel.smarttravel.repository.UserRepository;
import com.travel.smarttravel.security.JwtUtil;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, null, null,
                    "Username already taken"));
        }

        if (request.getEmail() != null &&
            userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, null, null,
                    "Email already registered"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        String token = jwtUtil.generateToken(
            user.getUsername(), user.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new AuthResponse(token, user.getUsername(),
                user.getRole(), "Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {

        User user = userRepository
            .findByUsername(request.getUsername())
            .orElse(null);

        if (user == null || !passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse(null, null, null,
                    "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(
            user.getUsername(), user.getRole());

        return ResponseEntity.ok(
            new AuthResponse(token, user.getUsername(),
                user.getRole(), "Login successful"));
    }
}