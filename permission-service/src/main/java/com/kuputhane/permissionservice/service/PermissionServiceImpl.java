package com.kuputhane.permissionservice.service;

import com.kuputhane.permissionservice.model.Permission;
import com.kuputhane.permissionservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public List<Permission> getAllByRoleRecursive(Integer roleId) {
        List<Permission> basePermissions = permissionRepository.findByRoleId(roleId);
        Set<Permission> result = new HashSet<>(basePermissions);

        for (Permission perm : basePermissions) {
            result.addAll(fetchChildren(perm));
        }

        return new ArrayList<>(result);
    }

    private List<Permission> fetchChildren(Permission parent) {
        List<Permission> children = permissionRepository.findByParent(parent);
        List<Permission> result = new ArrayList<>(children);

        for (Permission child : children) {
            result.addAll(fetchChildren(child)); // recursive DFS
        }

        return result;
    }
}