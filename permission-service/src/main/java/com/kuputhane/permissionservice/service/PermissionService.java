package com.kuputhane.permissionservice.service;

import com.kuputhane.permissionservice.model.Permission;
import java.util.List;

public interface PermissionService {
    List<Permission> getAll();
    Permission getById(Long id);
    Permission save(Permission permission);
    void delete(Long id);
    List<Permission> getAllByRoleRecursive(Integer roleId);
    boolean hasAccess(Integer roleId, Long permissionId);
}
