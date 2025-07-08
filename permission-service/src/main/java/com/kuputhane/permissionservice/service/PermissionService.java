package com.kuputhane.permissionservice.service;

import com.kuputhane.permissionservice.model.AccessPermission;
import java.util.List;
import java.util.Set;

public interface PermissionService {

    List<AccessPermission> getAll();
    AccessPermission getById(Long id);
    AccessPermission save(AccessPermission permission);
    void delete(Long id);

    List<AccessPermission> getAllByRoleRecursive(Integer roleId);
    boolean hasAccess(Integer roleId, Long permissionId);

    Set<AccessPermission> getAllAccessiblePermissions(Long permissionId);
    List<AccessPermission> getPermissionTree();
}
