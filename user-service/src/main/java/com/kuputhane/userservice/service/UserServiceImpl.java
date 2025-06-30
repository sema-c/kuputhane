package com.kuputhane.userservice.service;

import com.kuputhane.userservice.dto.RegisterRequest;
import com.kuputhane.userservice.model.Role;
import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest request) {
        if (repo.findByUsername(request.getUsername()).isPresent()) {
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if ("LIBRARIAN".equalsIgnoreCase(request.getRole())) {
            user.setRole(Role.LIBRARIAN);
        } else {
            user.setRole(Role.USER);
        }

        return repo.save(user);
    }

    @Override
    public Optional<User> loginByUsername(String username, String rawPassword) {
        return repo.findByUsername(username)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }

    @Override
    public void deleteUser(Long id) {
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
