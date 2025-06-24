package com.kuputhane.kuputhane.service;
import com.kuputhane.kuputhane.model.User;

public interface UserService {
    User register(User user);
    Object login(String email, String password);
}
