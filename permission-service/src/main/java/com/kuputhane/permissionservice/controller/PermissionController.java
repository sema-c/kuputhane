package com.kuputhane.permissionservice.controller;

import com.kuputhane.permissionservice.model.AccessPermission;
import com.kuputhane.permissionservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAccess(
            @RequestParam Integer roleId,
            @RequestParam Long permissionId) {
        boolean result = permissionService.hasAccess(roleId, permissionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<AccessPermission>> getAll() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessPermission> getById(@PathVariable Long id) {
        AccessPermission permission = permissionService.getById(id);
        return permission != null ? ResponseEntity.ok(permission) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AccessPermission> save(@RequestBody AccessPermission permission) {
        return ResponseEntity.ok(permissionService.save(permission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<AccessPermission>> getByRoleRecursive(@PathVariable Integer roleId) {
        return ResponseEntity.ok(permissionService.getAllByRoleRecursive(roleId));
    }

    @GetMapping("/accessible/{permissionId}")
    public ResponseEntity<Set<AccessPermission>> getAccessible(@PathVariable Long permissionId) {
        return ResponseEntity.ok(permissionService.getAllAccessiblePermissions(permissionId));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<AccessPermission>> getTree() {
        return ResponseEntity.ok(permissionService.getPermissionTree());
    }

    @PostMapping("/check/user")
    public ResponseEntity<Boolean> checkAccessForUser(
            @RequestBody Set<Long> userPermissionIds,
            @RequestParam Long permissionId) {

        for (Long permId : userPermissionIds) {
            Set<AccessPermission> accessible = permissionService.getAllAccessiblePermissions(permId);
            boolean has = accessible.stream().anyMatch(p -> Objects.equals(p.getId(), permissionId));
            if (has) return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

}