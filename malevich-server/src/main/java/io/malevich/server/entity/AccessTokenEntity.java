package io.malevich.server.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "access_token")
public class AccessTokenEntity implements Entity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column
    private String token;

    @Getter
    @ManyToOne
    private UserEntity user;

    @Getter
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

    public boolean isExpired() {
        if (null == this.expiry) {
            return false;
        }

        return this.expiry.getTime() > System.currentTimeMillis();
    }
}
