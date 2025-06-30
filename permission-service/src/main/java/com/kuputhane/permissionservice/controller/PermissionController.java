package com.kuputhane.permissionservice.controller;

import com.kuputhane.permissionservice.model.Permission;
import com.kuputhane.permissionservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public List<Permission> getAll() {
        return permissionService.getAll();
    }

    @GetMapping("/{id}")
    public Permission getById(@PathVariable Long id) {
        return permissionService.getById(id);
    }

    @GetMapping("/role/{roleId}")
    public List<Permission> getPermissionsByRole(@PathVariable Integer roleId) {
        return permissionService.getAllByRoleRecursive(roleId);
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }
}