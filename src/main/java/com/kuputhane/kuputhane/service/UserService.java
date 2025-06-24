package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.User;
import com.kuputhane.kuputhane.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User user) {
        return repo.save(user);
    }

    public Optional<User> login(String username, String password) {
        return repo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }


}
