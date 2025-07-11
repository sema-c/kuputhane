package com.kuputhane.userservice.service;

import com.kuputhane.permissionservice.model.AccessPermission;
import com.kuputhane.userservice.dto.RegisterRequest;
import com.kuputhane.userservice.model.Role;
import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
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

        return userRepository.save(user);
    }

    @Override
    public Optional<User> loginByUsername(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User incomingUser) {
        System.out.println("Güncelleme isteği: id=" + id + ", incomingUser=" + incomingUser);
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (incomingUser.getEmail() != null) {
            existing.setEmail(incomingUser.getEmail());
        }
        if (incomingUser.getPhoneNumber() != null) {
            existing.setPhoneNumber(incomingUser.getPhoneNumber());
        }

        User savedUser = userRepository.save(existing);
        System.out.println("Güncellenen kullanıcı: " + savedUser);
        return savedUser;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<AccessPermission> getPermissionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Integer roleId = user.getRole().ordinal();

        ResponseEntity<AccessPermission[]> response = restTemplate.getForEntity(
                "http://localhost:8083/api/permissions/role/" + roleId,
                AccessPermission[].class
        );

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
