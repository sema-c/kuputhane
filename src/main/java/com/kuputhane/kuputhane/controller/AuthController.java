package com.kuputhane.kuputhane.controller;
import com.kuputhane.kuputhane.model.User;
import com.kuputhane.kuputhane.service.UserService;
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
    public ResponseEntity<?> register(@RequestBody User user) {
        User created = service.register(user);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Kullanıcı zaten mevcut");
        }
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> found = service.loginByUsername(user.getUsername(), user.getPassword());
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz giriş");
        }
    }

}
