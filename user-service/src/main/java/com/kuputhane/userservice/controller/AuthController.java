package com.kuputhane.userservice.controller;

import com.kuputhane.userservice.dto.LoginRequest;
import com.kuputhane.userservice.dto.RegisterRequest;
import com.kuputhane.userservice.dto.RegisterResponse;
import com.kuputhane.userservice.model.Role;
import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
import com.kuputhane.userservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User created = service.register(request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Kullanıcı zaten mevcut");
        }
        return ResponseEntity.ok(new RegisterResponse(created.getUsername(), created.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = service.loginByUsername(request.getUsername(), request.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz giriş");
    }
}
