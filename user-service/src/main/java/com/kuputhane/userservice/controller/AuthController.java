package com.kuputhane.userservice.controller;

import com.kuputhane.userservice.dto.LoginRequest;
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
    private final UserRepository userRepository;

    public AuthController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
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

    @PostMapping("/login/librarian")
    public ResponseEntity<?> loginLibrarian(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmailAndRole(request.getEmail(), Role.LIBRARIAN);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPasswordHash().equals(request.getPassword())) {
                return ResponseEntity.ok(user); // Giriş başarılı, user objesi döner
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Şifre yanlış.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Librarian bulunamadı.");
        }
    }
}
