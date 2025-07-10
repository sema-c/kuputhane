package com.kuputhane.userservice.controller;

import com.kuputhane.permissionservice.model.AccessPermission;
import com.kuputhane.userservice.dto.RegisterRequest;
import com.kuputhane.userservice.model.User;
import com.kuputhane.userservice.repository.UserRepository;
import com.kuputhane.userservice.service.UserService;
import com.kuputhane.userservice.util.RoleAccessChecker;
import com.kuputhane.userservice.util.RoleHierarchyBuilder;
import com.kuputhane.userservice.util.RoleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final RoleNode roleTree = RoleHierarchyBuilder.buildRoleTree();

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("role") String requesterRole) {
        if (!RoleAccessChecker.hasAccess(requesterRole, "LIBRARIAN", roleTree)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erişim reddedildi");
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return userService.loginByUsername(username, password)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, @RequestHeader("role") String requesterRole) {
        if (!RoleAccessChecker.hasAccess(requesterRole, "LIBRARIAN", roleTree)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erişim reddedildi");
        }

        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/users/{id}/role")
    public ResponseEntity<String> getUserRole(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt
                .map(user -> ResponseEntity.ok(user.getRole().name()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user, @RequestHeader("role") String requesterRole) {
        if (!RoleAccessChecker.hasAccess(requesterRole, "LIBRARIAN", roleTree)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erişim reddedildi");
        }

        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("role") String requesterRole) {
        if (!RoleAccessChecker.hasAccess(requesterRole, "LIBRARIAN", roleTree)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erişim reddedildi");
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<Long>> getUserPermissions(@PathVariable Long id) {
        List<AccessPermission> permissions = userService.getPermissionsByUserId(id);
        List<Long> ids = permissions.stream()
                .map(AccessPermission::getId)
                .toList();
        return ResponseEntity.ok(ids);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/count")
    public Map<String, Long> getUserCount() {
        long totalUsers = userRepository.count();
        return Map.of("count", totalUsers);
    }
}
