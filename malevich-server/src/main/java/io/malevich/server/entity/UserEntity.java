package io.malevich.server.entity;

import io.malevich.server.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity(name = "user")
@Table(name = "user")
public class UserEntity implements Entity, UserDetails {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();

    @Getter
    @Setter
    @Column(name = "activity_flag")
    private boolean activityFlag;

    protected UserEntity() {
    }

    public UserEntity(String name, String passwordHash) {
        this.name = name;
        this.password = passwordHash;
    }

    public UserEntity(String name, String password, Set<Role> roles, boolean activityFlag) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.activityFlag = activityFlag;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activityFlag;
    }
}
