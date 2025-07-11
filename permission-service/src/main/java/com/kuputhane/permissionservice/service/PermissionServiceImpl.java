package com.kuputhane.permissionservice.service;

import com.kuputhane.permissionservice.model.AccessPermission;
import com.kuputhane.permissionservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<AccessPermission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public AccessPermission getById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public AccessPermission save(AccessPermission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public List<AccessPermission> getAllByRoleRecursive(Integer roleId) {
        List<AccessPermission> basePermissions = permissionRepository.findByRoleId(roleId);
        Set<AccessPermission> result = new HashSet<>(basePermissions);

        for (AccessPermission perm : basePermissions) {
            result.addAll(fetchChildren(perm));
        }
        return new ArrayList<>(result);
    }

    private List<AccessPermission> fetchChildren(AccessPermission parent) {
        List<AccessPermission> children = permissionRepository.findByParent(parent);
        List<AccessPermission> result = new ArrayList<>(children);

        for (AccessPermission child : children) {
            result.addAll(fetchChildren(child));
        }
        return result;
    }

    @Override
    public boolean hasAccess(Integer roleId, Long permissionId) {
        List<AccessPermission> allPermissions = getAllByRoleRecursive(roleId);
        return allPermissions.stream()
                .anyMatch(p -> Objects.equals(p.getId(), permissionId));
    }

    @Override
    public Set<AccessPermission> getAllAccessiblePermissions(Long permissionId) {
        Optional<AccessPermission> opt = permissionRepository.findById(permissionId);
        if (opt.isEmpty()) return Set.of();

        Set<AccessPermission> result = new HashSet<>();
        collectChildren(opt.get(), result);
        return result;
    }

    private void collectChildren(AccessPermission permission, Set<AccessPermission> result) {
        result.add(permission);
        for (AccessPermission child : permission.getChildren()) {
            collectChildren(child, result);
        }
    }

    @Override
    public List<AccessPermission> getPermissionTree() {
        return permissionRepository.findAll()
                .stream()
                .filter(p -> p.getParent() == null)
                .toList();
    }
}
