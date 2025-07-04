package com.kuputhane.permissionservice.controller;

import com.kuputhane.permissionservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/access")
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
}
