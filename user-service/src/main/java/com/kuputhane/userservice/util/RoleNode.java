package com.kuputhane.userservice.util;

import java.util.ArrayList;
import java.util.List;

public class RoleNode {
    private String roleName;
    private List<RoleNode> children;

    public RoleNode(String roleName) {
        this.roleName = roleName;
        this.children = new ArrayList<>();
    }

    public void addChild(RoleNode child) {
        children.add(child);
    }

    public String getRoleName() {
        return roleName;
    }

    public List<RoleNode> getChildren() {
        return children;
    }
}

