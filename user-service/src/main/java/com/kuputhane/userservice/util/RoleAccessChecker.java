package com.kuputhane.userservice.util;

public class RoleAccessChecker {

    public static boolean hasAccess(String userRole, String requiredRole, RoleNode currentNode) {
        if (currentNode.getRoleName().equals(userRole)) {
            return containsRole(currentNode, requiredRole);
        }

        for (RoleNode child : currentNode.getChildren()) {
            if (hasAccess(userRole, requiredRole, child)) {
                return true;
            }
        }

        return false;
    }

    private static boolean containsRole(RoleNode node, String role) {
        if (node.getRoleName().equals(role)) {
            return true;
        }

        for (RoleNode child : node.getChildren()) {
            if (containsRole(child, role)) {
                return true;
            }
        }

        return false;
    }
}
