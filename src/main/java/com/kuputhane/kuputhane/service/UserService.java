package com.kuputhane.kuputhane.service;

import com.kuputhane.kuputhane.model.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> loginByUsername(String username, String password);
}
