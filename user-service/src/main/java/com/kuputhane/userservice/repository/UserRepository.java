package com.kuputhane.userservice.repository;

import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndRole(String email, Role role);
}
