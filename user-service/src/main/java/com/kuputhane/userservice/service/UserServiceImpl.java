package com.kuputhane.userservice.service;

import com.kuputhane.userservice.model.Role;
import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User register(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return null;
        }

        String hashedPassword = hashPassword(user.getPassword());
        user.setPasswordHash(hashedPassword);
        user.setPassword(null);

        user.setRole(Role.USER);

        return repo.save(user);
    }


    @Override
    public Optional<User> loginByUsername(String username, String password) {
        return repo.findByUsername(username)
                .filter(u -> checkPassword(password, u.getPasswordHash()));
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
        return List.of();
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // örnek hash
    }

    private boolean checkPassword(String raw, String hashed) {
        return hashPassword(raw).equals(hashed);
    }
}
