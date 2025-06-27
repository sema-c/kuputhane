package com.kuputhane.userservice.service;

import com.kuputhane.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> loginByUsername(String username, String password);

    void deleteUser(Long id);

    User updateUser(Long id, User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}
