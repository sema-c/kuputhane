package com.kuputhane.userservice.service;

import com.kuputhane.userservice.model.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> loginByUsername(String username, String password);
}
