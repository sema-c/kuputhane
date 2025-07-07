package com.kuputhane.permissionservice.repository;

import com.kuputhane.permissionservice.model.AccessPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRepository extends JpaRepository<AccessPermission, Long> {

    List<AccessPermission> findByRoleId(Integer roleId);
    List<AccessPermission> findByParent(AccessPermission parent);
}
