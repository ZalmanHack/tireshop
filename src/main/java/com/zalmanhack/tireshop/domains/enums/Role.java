package com.zalmanhack.tireshop.domains.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER(Names.USER),
    ADMIN(Names.ADMIN);
    private final String label;

    public static class Names{
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

    Role(String label) {
        this.label = label;
    }

    @Override
    public String getAuthority() {
        return this.label;
    }
}
