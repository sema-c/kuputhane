package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.User;
import com.kuputhane.kuputhane.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User register(User user) {
        user.setPasswordHash(hashPassword(user.getPasswordHash())); // güvenli hale getir
        return repo.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return repo.findByEmail(email)
                .filter(u -> checkPassword(password, u.getPasswordHash()));
    }


    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // örnek hash
    }

    private boolean checkPassword(String raw, String hashed) {
        return hashPassword(raw).equals(hashed);
    }
}
