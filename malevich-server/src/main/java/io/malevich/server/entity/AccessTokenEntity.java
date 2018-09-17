package io.malevich.server.entity;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "access_token")
public class AccessTokenEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    private UserEntity user;

    @Column
    private Date expiry;

    protected AccessTokenEntity() {

    }

    public AccessTokenEntity(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }

    public AccessTokenEntity(UserEntity user, String token, Date expiry) {
        this(user, token);
        this.expiry = expiry;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public Date getExpiry() {
        return this.expiry;
    }

    public boolean isExpired() {
        if (null == this.expiry) {
            return false;
        }

        return this.expiry.getTime() > System.currentTimeMillis();
    }
}
