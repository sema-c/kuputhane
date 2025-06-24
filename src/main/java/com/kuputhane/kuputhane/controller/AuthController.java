package com.kuputhane.kuputhane.controller;
import com.kuputhane.kuputhane.model.User;
import com.kuputhane.kuputhane.service.UserService;
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
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        Optional<User> found = service.login(user.getUsername(), user.getPassword());
        return found.orElse(null);
    }
}
