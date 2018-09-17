package io.malevich.server.dao.accesstoken;


import io.malevich.server.dao.Dao;
import io.malevich.server.entity.AccessTokenEntity;
import org.springframework.stereotype.Component;


@Component
public interface AccessTokenDao extends Dao<AccessTokenEntity, Long> {
    AccessTokenEntity findByToken(String accessTokenString);
}
