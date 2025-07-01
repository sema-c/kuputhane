package com.kuputhane.permissionservice.repository;

import com.kuputhane.permissionservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByRoleId(Integer roleId);
    List<Permission> findByParent(Permission parent);
}
