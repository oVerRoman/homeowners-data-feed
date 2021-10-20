package com.simbirsoftintensiv.intensiv.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    public static Role fromString(String text) {
        for (Role role : Role.values()) {
            if (role.getAuthority().equalsIgnoreCase("ROLE_" + text)) {
                return role;
            }
        }
        return null;
    }

    //    https://stackoverflow.com/a/19542316/548473
    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
