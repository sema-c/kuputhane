package com.kuputhane.permissionservice.service;

import com.kuputhane.permissionservice.model.Permission;
import java.util.List;

public interface PermissionService {
    List<Permission> getAll();
    List<Permission> getAllByRoleRecursive(Integer roleId);
    Permission getById(Long id);
    Permission save(Permission permission);
    void delete(Long id);
}