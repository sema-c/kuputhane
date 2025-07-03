package com.kuputhane.userservice.util;

public class RoleHierarchyBuilder {

    public static RoleNode buildRoleTree() {
        RoleNode user = new RoleNode("USER");
        RoleNode librarian = new RoleNode("LIBRARIAN");
        librarian.addChild(user);
        RoleNode admin = new RoleNode("ADMIN");
        admin.addChild(librarian);

        return admin; // root
    }
}
