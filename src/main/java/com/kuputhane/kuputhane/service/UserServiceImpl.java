package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.User;
import com.kuputhane.kuputhane.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User register(User user) {
        user.setPasswordHash(hashPassword(user.getPasswordHash()));
        return repo.save(user);
    }

    @Override
    public Object login(String email, String password) {
        return repo.findByEmail(email)
                .filter(u -> checkPassword(password, String.valueOf(u.hashCode())));
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    private boolean checkPassword(String raw, String hashed) {
        return hashPassword(raw).equals(hashed);
    }
}
