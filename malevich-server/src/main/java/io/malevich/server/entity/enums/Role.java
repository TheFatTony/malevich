package io.malevich.server.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), GALLERY("ROLE_GALLERY"), TRADER("ROLE_TRADER");

    private String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
