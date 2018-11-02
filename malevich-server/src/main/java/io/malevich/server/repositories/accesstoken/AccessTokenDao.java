package io.malevich.server.repositories.accesstoken;


import io.malevich.server.domain.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccessTokenDao extends JpaRepository<AccessTokenEntity, Long> {
    AccessTokenEntity findByToken(String token);
}
