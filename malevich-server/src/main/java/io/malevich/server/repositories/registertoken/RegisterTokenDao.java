package io.malevich.server.repositories.registertoken;


import io.malevich.server.domain.RegisterTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterTokenDao extends JpaRepository<RegisterTokenEntity, Long> {

    Optional<RegisterTokenEntity> findByToken(String token);

}
