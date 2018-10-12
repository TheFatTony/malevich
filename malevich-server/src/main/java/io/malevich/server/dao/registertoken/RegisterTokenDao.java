package io.malevich.server.dao.registertoken;


import io.malevich.server.entity.RegisterTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterTokenDao extends JpaRepository<RegisterTokenEntity, Long> {

    Optional<RegisterTokenEntity> findByToken(String token);

}
