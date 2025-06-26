package com.kuputhane.userservice.service;

import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
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
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return null; // kullanıcı zaten var
        }

        String hashedPassword = hashPassword(user.getPassword());
        user.setPasswordHash(hashedPassword); // doğru alanı kullan!
        return repo.save(user);
    }

    @Override
    public Optional<User> loginByUsername(String username, String password) {
        return repo.findByUsername(username)
                .filter(u -> checkPassword(password, u.getPasswordHash()));
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // örnek hash
    }

    private boolean checkPassword(String raw, String hashed) {
        return hashPassword(raw).equals(hashed);
    }
}
