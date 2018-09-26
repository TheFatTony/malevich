package io.malevich.server.dao.accesstoken;


import io.malevich.server.entity.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface AccessTokenDao extends JpaRepository<AccessTokenEntity, Long> {
    AccessTokenEntity findByToken(String token);
}
